package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.service.ContactoService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.service.dto.ContactoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.Contacto}.
 */
@RestController
@RequestMapping("/api")
public class ContactoResource {

    private final Logger log = LoggerFactory.getLogger(ContactoResource.class);

    private static final String ENTITY_NAME = "contacto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactoService contactoService;

    public ContactoResource(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    /**
     * {@code POST  /contactos} : Create a new contacto.
     *
     * @param contactoDTO the contactoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactoDTO, or with status {@code 400 (Bad Request)} if the contacto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contactos")
    public ResponseEntity<ContactoDTO> createContacto(@RequestBody ContactoDTO contactoDTO) throws URISyntaxException {
        log.debug("REST request to save Contacto : {}", contactoDTO);
        if (contactoDTO.getId() != null) {
            throw new BadRequestAlertException("A new contacto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactoDTO result = contactoService.save(contactoDTO);
        return ResponseEntity.created(new URI("/api/contactos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contactos} : Updates an existing contacto.
     *
     * @param contactoDTO the contactoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactoDTO,
     * or with status {@code 400 (Bad Request)} if the contactoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contactos")
    public ResponseEntity<ContactoDTO> updateContacto(@RequestBody ContactoDTO contactoDTO) throws URISyntaxException {
        log.debug("REST request to update Contacto : {}", contactoDTO);
        if (contactoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactoDTO result = contactoService.save(contactoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contactos} : get all the contactos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactos in body.
     */
    @GetMapping("/contactos")
    public List<ContactoDTO> getAllContactos() {
        log.debug("REST request to get all Contactos");
        return contactoService.findAll();
    }

    /**
     * {@code GET  /contactos/:id} : get the "id" contacto.
     *
     * @param id the id of the contactoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contactos/{id}")
    public ResponseEntity<ContactoDTO> getContacto(@PathVariable Long id) {
        log.debug("REST request to get Contacto : {}", id);
        Optional<ContactoDTO> contactoDTO = contactoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactoDTO);
    }

    /**
     * {@code DELETE  /contactos/:id} : delete the "id" contacto.
     *
     * @param id the id of the contactoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contactos/{id}")
    public ResponseEntity<Void> deleteContacto(@PathVariable Long id) {
        log.debug("REST request to delete Contacto : {}", id);
        contactoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
