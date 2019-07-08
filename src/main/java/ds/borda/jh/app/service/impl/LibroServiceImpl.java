package ds.borda.jh.app.service.impl;

import ds.borda.jh.app.service.LibroService;
import ds.borda.jh.app.domain.Libro;
import ds.borda.jh.app.repository.LibroRepository;
import ds.borda.jh.app.service.dto.LibroDTO;
import ds.borda.jh.app.service.mapper.LibroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Libro}.
 */
@Service
@Transactional
public class LibroServiceImpl implements LibroService {

    private final Logger log = LoggerFactory.getLogger(LibroServiceImpl.class);

    private final LibroRepository libroRepository;

    private final LibroMapper libroMapper;

    public LibroServiceImpl(LibroRepository libroRepository, LibroMapper libroMapper) {
        this.libroRepository = libroRepository;
        this.libroMapper = libroMapper;
    }

    /**
     * Save a libro.
     *
     * @param libroDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LibroDTO save(LibroDTO libroDTO) {
        log.debug("Request to save Libro : {}", libroDTO);
        Libro libro = libroMapper.toEntity(libroDTO);
        libro = libroRepository.save(libro);
        return libroMapper.toDto(libro);
    }

    /**
     * Get all the libros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LibroDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Libros");
        return libroRepository.findAll(pageable)
            .map(libroMapper::toDto);
    }


    /**
     * Get one libro by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LibroDTO> findOne(Long id) {
        log.debug("Request to get Libro : {}", id);
        return libroRepository.findById(id)
            .map(libroMapper::toDto);
    }

    /**
     * Delete the libro by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Libro : {}", id);
        libroRepository.deleteById(id);
    }
}
