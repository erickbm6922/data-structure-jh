package ds.borda.jh.app.service;

import ds.borda.jh.app.service.dto.LibroDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ds.borda.jh.app.domain.Libro}.
 */
public interface LibroService {

    /**
     * Save a libro.
     *
     * @param libroDTO the entity to save.
     * @return the persisted entity.
     */
    LibroDTO save(LibroDTO libroDTO);

    /**
     * Get all the libros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LibroDTO> findAll(Pageable pageable);


    /**
     * Get the "id" libro.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LibroDTO> findOne(Long id);

    /**
     * Delete the "id" libro.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
