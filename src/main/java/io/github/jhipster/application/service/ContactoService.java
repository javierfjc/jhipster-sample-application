package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.ContactoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.jhipster.application.domain.Contacto}.
 */
public interface ContactoService {

    /**
     * Save a contacto.
     *
     * @param contactoDTO the entity to save.
     * @return the persisted entity.
     */
    ContactoDTO save(ContactoDTO contactoDTO);

    /**
     * Get all the contactos.
     *
     * @return the list of entities.
     */
    List<ContactoDTO> findAll();


    /**
     * Get the "id" contacto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContactoDTO> findOne(Long id);

    /**
     * Delete the "id" contacto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
