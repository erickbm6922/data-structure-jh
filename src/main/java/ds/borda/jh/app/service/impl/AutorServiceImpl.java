package ds.borda.jh.app.service.impl;

import ds.borda.jh.app.service.AutorService;
import ds.borda.jh.app.domain.Autor;
import ds.borda.jh.app.repository.AutorRepository;
import ds.borda.jh.app.service.dto.AutorDTO;
import ds.borda.jh.app.service.mapper.AutorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Autor}.
 */
@Service
@Transactional
public class AutorServiceImpl implements AutorService {

    private final Logger log = LoggerFactory.getLogger(AutorServiceImpl.class);

    private final AutorRepository autorRepository;

    private final AutorMapper autorMapper;

    public AutorServiceImpl(AutorRepository autorRepository, AutorMapper autorMapper) {
        this.autorRepository = autorRepository;
        this.autorMapper = autorMapper;
    }

    /**
     * Save a autor.
     *
     * @param autorDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AutorDTO save(AutorDTO autorDTO) {
        log.debug("Request to save Autor : {}", autorDTO);
        Autor autor = autorMapper.toEntity(autorDTO);
        autor = autorRepository.save(autor);
        return autorMapper.toDto(autor);
    }

    /**
     * Get all the autors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AutorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Autors");
        return autorRepository.findAll(pageable)
            .map(autorMapper::toDto);
    }

    /**
     * Get all the autors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AutorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return autorRepository.findAllWithEagerRelationships(pageable).map(autorMapper::toDto);
    }
    

    /**
     * Get one autor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AutorDTO> findOne(Long id) {
        log.debug("Request to get Autor : {}", id);
        return autorRepository.findOneWithEagerRelationships(id)
            .map(autorMapper::toDto);
    }

    /**
     * Delete the autor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Autor : {}", id);
        autorRepository.deleteById(id);
    }
}
