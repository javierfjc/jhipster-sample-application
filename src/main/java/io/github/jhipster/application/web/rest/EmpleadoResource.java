package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.service.EmpleadoService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.service.dto.EmpleadoDTO;

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
 * REST controller for managing {@link io.github.jhipster.application.domain.Empleado}.
 */
@RestController
@RequestMapping("/api")
public class EmpleadoResource {

    private final Logger log = LoggerFactory.getLogger(EmpleadoResource.class);

    private static final String ENTITY_NAME = "empleado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpleadoService empleadoService;

    public EmpleadoResource(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    /**
     * {@code POST  /empleados} : Create a new empleado.
     *
     * @param empleadoDTO the empleadoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empleadoDTO, or with status {@code 400 (Bad Request)} if the empleado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empleados")
    public ResponseEntity<EmpleadoDTO> createEmpleado(@RequestBody EmpleadoDTO empleadoDTO) throws URISyntaxException {
        log.debug("REST request to save Empleado : {}", empleadoDTO);
        if (empleadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new empleado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmpleadoDTO result = empleadoService.save(empleadoDTO);
        return ResponseEntity.created(new URI("/api/empleados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empleados} : Updates an existing empleado.
     *
     * @param empleadoDTO the empleadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empleadoDTO,
     * or with status {@code 400 (Bad Request)} if the empleadoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empleadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empleados")
    public ResponseEntity<EmpleadoDTO> updateEmpleado(@RequestBody EmpleadoDTO empleadoDTO) throws URISyntaxException {
        log.debug("REST request to update Empleado : {}", empleadoDTO);
        if (empleadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmpleadoDTO result = empleadoService.save(empleadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empleadoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /empleados} : get all the empleados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empleados in body.
     */
    @GetMapping("/empleados")
    public List<EmpleadoDTO> getAllEmpleados() {
        log.debug("REST request to get all Empleados");
        return empleadoService.findAll();
    }

    /**
     * {@code GET  /empleados/:id} : get the "id" empleado.
     *
     * @param id the id of the empleadoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empleadoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empleados/{id}")
    public ResponseEntity<EmpleadoDTO> getEmpleado(@PathVariable Long id) {
        log.debug("REST request to get Empleado : {}", id);
        Optional<EmpleadoDTO> empleadoDTO = empleadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empleadoDTO);
    }

    /**
     * {@code DELETE  /empleados/:id} : delete the "id" empleado.
     *
     * @param id the id of the empleadoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Long id) {
        log.debug("REST request to delete Empleado : {}", id);
        empleadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
