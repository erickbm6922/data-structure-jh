package ds.borda.jh.app.web.rest;

import ds.borda.jh.app.DataStructureWebApp;
import ds.borda.jh.app.domain.Venta;
import ds.borda.jh.app.repository.VentaRepository;
import ds.borda.jh.app.service.VentaService;
import ds.borda.jh.app.service.dto.VentaDTO;
import ds.borda.jh.app.service.mapper.VentaMapper;
import ds.borda.jh.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static ds.borda.jh.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link VentaResource} REST controller.
 */
@SpringBootTest(classes = DataStructureWebApp.class)
public class VentaResourceIT {

    private static final String DEFAULT_FOLIO = "AAAAAAAAAA";
    private static final String UPDATED_FOLIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_SUBTOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBTOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_IMPUESTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_IMPUESTO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VentaMapper ventaMapper;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restVentaMockMvc;

    private Venta venta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VentaResource ventaResource = new VentaResource(ventaService);
        this.restVentaMockMvc = MockMvcBuilders.standaloneSetup(ventaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venta createEntity(EntityManager em) {
        Venta venta = new Venta()
            .folio(DEFAULT_FOLIO)
            .fecha(DEFAULT_FECHA)
            .subtotal(DEFAULT_SUBTOTAL)
            .impuesto(DEFAULT_IMPUESTO)
            .total(DEFAULT_TOTAL);
        return venta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venta createUpdatedEntity(EntityManager em) {
        Venta venta = new Venta()
            .folio(UPDATED_FOLIO)
            .fecha(UPDATED_FECHA)
            .subtotal(UPDATED_SUBTOTAL)
            .impuesto(UPDATED_IMPUESTO)
            .total(UPDATED_TOTAL);
        return venta;
    }

    @BeforeEach
    public void initTest() {
        venta = createEntity(em);
    }

    @Test
    @Transactional
    public void createVenta() throws Exception {
        int databaseSizeBeforeCreate = ventaRepository.findAll().size();

        // Create the Venta
        VentaDTO ventaDTO = ventaMapper.toDto(venta);
        restVentaMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isCreated());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeCreate + 1);
        Venta testVenta = ventaList.get(ventaList.size() - 1);
        assertThat(testVenta.getFolio()).isEqualTo(DEFAULT_FOLIO);
        assertThat(testVenta.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testVenta.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testVenta.getImpuesto()).isEqualTo(DEFAULT_IMPUESTO);
        assertThat(testVenta.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createVentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ventaRepository.findAll().size();

        // Create the Venta with an existing ID
        venta.setId(1L);
        VentaDTO ventaDTO = ventaMapper.toDto(venta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVentaMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVentas() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        // Get all the ventaList
        restVentaMockMvc.perform(get("/api/ventas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venta.getId().intValue())))
            .andExpect(jsonPath("$.[*].folio").value(hasItem(DEFAULT_FOLIO.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getVenta() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        // Get the venta
        restVentaMockMvc.perform(get("/api/ventas/{id}", venta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(venta.getId().intValue()))
            .andExpect(jsonPath("$.folio").value(DEFAULT_FOLIO.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.intValue()))
            .andExpect(jsonPath("$.impuesto").value(DEFAULT_IMPUESTO.intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVenta() throws Exception {
        // Get the venta
        restVentaMockMvc.perform(get("/api/ventas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVenta() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        int databaseSizeBeforeUpdate = ventaRepository.findAll().size();

        // Update the venta
        Venta updatedVenta = ventaRepository.findById(venta.getId()).get();
        // Disconnect from session so that the updates on updatedVenta are not directly saved in db
        em.detach(updatedVenta);
        updatedVenta
            .folio(UPDATED_FOLIO)
            .fecha(UPDATED_FECHA)
            .subtotal(UPDATED_SUBTOTAL)
            .impuesto(UPDATED_IMPUESTO)
            .total(UPDATED_TOTAL);
        VentaDTO ventaDTO = ventaMapper.toDto(updatedVenta);

        restVentaMockMvc.perform(put("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isOk());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeUpdate);
        Venta testVenta = ventaList.get(ventaList.size() - 1);
        assertThat(testVenta.getFolio()).isEqualTo(UPDATED_FOLIO);
        assertThat(testVenta.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testVenta.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testVenta.getImpuesto()).isEqualTo(UPDATED_IMPUESTO);
        assertThat(testVenta.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingVenta() throws Exception {
        int databaseSizeBeforeUpdate = ventaRepository.findAll().size();

        // Create the Venta
        VentaDTO ventaDTO = ventaMapper.toDto(venta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVentaMockMvc.perform(put("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVenta() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        int databaseSizeBeforeDelete = ventaRepository.findAll().size();

        // Delete the venta
        restVentaMockMvc.perform(delete("/api/ventas/{id}", venta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Venta.class);
        Venta venta1 = new Venta();
        venta1.setId(1L);
        Venta venta2 = new Venta();
        venta2.setId(venta1.getId());
        assertThat(venta1).isEqualTo(venta2);
        venta2.setId(2L);
        assertThat(venta1).isNotEqualTo(venta2);
        venta1.setId(null);
        assertThat(venta1).isNotEqualTo(venta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VentaDTO.class);
        VentaDTO ventaDTO1 = new VentaDTO();
        ventaDTO1.setId(1L);
        VentaDTO ventaDTO2 = new VentaDTO();
        assertThat(ventaDTO1).isNotEqualTo(ventaDTO2);
        ventaDTO2.setId(ventaDTO1.getId());
        assertThat(ventaDTO1).isEqualTo(ventaDTO2);
        ventaDTO2.setId(2L);
        assertThat(ventaDTO1).isNotEqualTo(ventaDTO2);
        ventaDTO1.setId(null);
        assertThat(ventaDTO1).isNotEqualTo(ventaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ventaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ventaMapper.fromId(null)).isNull();
    }
}
