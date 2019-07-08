package ds.borda.jh.app.web.rest;

import ds.borda.jh.app.service.AutorService;
import ds.borda.jh.app.web.rest.errors.BadRequestAlertException;
import ds.borda.jh.app.service.dto.AutorDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ds.borda.jh.app.domain.Autor}.
 */
@RestController
@RequestMapping("/api")
public class AutorResource {

    private final Logger log = LoggerFactory.getLogger(AutorResource.class);

    private static final String ENTITY_NAME = "autor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutorService autorService;

    public AutorResource(AutorService autorService) {
        this.autorService = autorService;
    }

    /**
     * {@code POST  /autors} : Create a new autor.
     *
     * @param autorDTO the autorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autorDTO, or with status {@code 400 (Bad Request)} if the autor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/autors")
    public ResponseEntity<AutorDTO> createAutor(@RequestBody AutorDTO autorDTO) throws URISyntaxException {
        log.debug("REST request to save Autor : {}", autorDTO);
        if (autorDTO.getId() != null) {
            throw new BadRequestAlertException("A new autor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutorDTO result = autorService.save(autorDTO);
        return ResponseEntity.created(new URI("/api/autors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /autors} : Updates an existing autor.
     *
     * @param autorDTO the autorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autorDTO,
     * or with status {@code 400 (Bad Request)} if the autorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/autors")
    public ResponseEntity<AutorDTO> updateAutor(@RequestBody AutorDTO autorDTO) throws URISyntaxException {
        log.debug("REST request to update Autor : {}", autorDTO);
        if (autorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutorDTO result = autorService.save(autorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /autors} : get all the autors.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autors in body.
     */
    @GetMapping("/autors")
    public ResponseEntity<List<AutorDTO>> getAllAutors(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Autors");
        Page<AutorDTO> page;
        if (eagerload) {
            page = autorService.findAllWithEagerRelationships(pageable);
        } else {
            page = autorService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /autors/:id} : get the "id" autor.
     *
     * @param id the id of the autorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/autors/{id}")
    public ResponseEntity<AutorDTO> getAutor(@PathVariable Long id) {
        log.debug("REST request to get Autor : {}", id);
        Optional<AutorDTO> autorDTO = autorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autorDTO);
    }

    /**
     * {@code DELETE  /autors/:id} : delete the "id" autor.
     *
     * @param id the id of the autorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/autors/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {
        log.debug("REST request to delete Autor : {}", id);
        autorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
