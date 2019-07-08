package ds.borda.jh.app.service.mapper;

import ds.borda.jh.app.domain.*;
import ds.borda.jh.app.service.dto.LibroDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Libro} and its DTO {@link LibroDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibroMapper extends EntityMapper<LibroDTO, Libro> {



    default Libro fromId(Long id) {
        if (id == null) {
            return null;
        }
        Libro libro = new Libro();
        libro.setId(id);
        return libro;
    }
}
