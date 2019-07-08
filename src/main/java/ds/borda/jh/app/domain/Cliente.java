package ds.borda.jh.app.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "correo")
    private String correo;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "cliente_venta",
               joinColumns = @JoinColumn(name = "cliente_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "venta_id", referencedColumnName = "id"))
    private Set<Venta> ventas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public Cliente razonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
        return this;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCorreo() {
        return correo;
    }

    public Cliente correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Set<Venta> getVentas() {
        return ventas;
    }

    public Cliente ventas(Set<Venta> ventas) {
        this.ventas = ventas;
        return this;
    }

    public Cliente addVenta(Venta venta) {
        this.ventas.add(venta);
        venta.getClientes().add(this);
        return this;
    }

    public Cliente removeVenta(Venta venta) {
        this.ventas.remove(venta);
        venta.getClientes().remove(this);
        return this;
    }

    public void setVentas(Set<Venta> ventas) {
        this.ventas = ventas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", correo='" + getCorreo() + "'" +
            "}";
    }
}
