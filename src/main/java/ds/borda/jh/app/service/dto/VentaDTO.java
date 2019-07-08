package ds.borda.jh.app.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link ds.borda.jh.app.domain.Venta} entity.
 */
public class VentaDTO implements Serializable {

    private Long id;

    private String folio;

    private LocalDate fecha;

    private BigDecimal subtotal;

    private BigDecimal impuesto;

    private BigDecimal total;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VentaDTO ventaDTO = (VentaDTO) o;
        if (ventaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ventaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VentaDTO{" +
            "id=" + getId() +
            ", folio='" + getFolio() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", subtotal=" + getSubtotal() +
            ", impuesto=" + getImpuesto() +
            ", total=" + getTotal() +
            "}";
    }
}
