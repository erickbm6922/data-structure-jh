package ds.borda.jh.app.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link ds.borda.jh.app.domain.Autor} entity.
 */
public class AutorDTO implements Serializable {

    private Long id;

    private String nombre;

    private String correo;


    private Set<LibroDTO> libros = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Set<LibroDTO> getLibros() {
        return libros;
    }

    public void setLibros(Set<LibroDTO> libros) {
        this.libros = libros;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AutorDTO autorDTO = (AutorDTO) o;
        if (autorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), autorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutorDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", correo='" + getCorreo() + "'" +
            "}";
    }
}
