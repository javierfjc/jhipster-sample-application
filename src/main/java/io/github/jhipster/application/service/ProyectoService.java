package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.ProyectoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.jhipster.application.domain.Proyecto}.
 */
public interface ProyectoService {

    /**
     * Save a proyecto.
     *
     * @param proyectoDTO the entity to save.
     * @return the persisted entity.
     */
    ProyectoDTO save(ProyectoDTO proyectoDTO);

    /**
     * Get all the proyectos.
     *
     * @return the list of entities.
     */
    List<ProyectoDTO> findAll();


    /**
     * Get the "id" proyecto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProyectoDTO> findOne(Long id);

    /**
     * Delete the "id" proyecto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
