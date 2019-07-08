package ds.borda.jh.app.service.mapper;

import ds.borda.jh.app.domain.*;
import ds.borda.jh.app.service.dto.AutorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Autor} and its DTO {@link AutorDTO}.
 */
@Mapper(componentModel = "spring", uses = {LibroMapper.class})
public interface AutorMapper extends EntityMapper<AutorDTO, Autor> {


    @Mapping(target = "removeLibro", ignore = true)

    default Autor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Autor autor = new Autor();
        autor.setId(id);
        return autor;
    }
}
