package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.TareaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.jhipster.application.domain.Tarea}.
 */
public interface TareaService {

    /**
     * Save a tarea.
     *
     * @param tareaDTO the entity to save.
     * @return the persisted entity.
     */
    TareaDTO save(TareaDTO tareaDTO);

    /**
     * Get all the tareas.
     *
     * @return the list of entities.
     */
    List<TareaDTO> findAll();


    /**
     * Get the "id" tarea.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TareaDTO> findOne(Long id);

    /**
     * Delete the "id" tarea.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
