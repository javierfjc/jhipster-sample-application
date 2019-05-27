package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhispterOrclApp;
import io.github.jhipster.application.domain.Tarea;
import io.github.jhipster.application.repository.TareaRepository;
import io.github.jhipster.application.service.TareaService;
import io.github.jhipster.application.service.dto.TareaDTO;
import io.github.jhipster.application.service.mapper.TareaMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.TareasEstado;
/**
 * Integration tests for the {@Link TareaResource} REST controller.
 */
@SpringBootTest(classes = JhispterOrclApp.class)
public class TareaResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final TareasEstado DEFAULT_ESTADO = TareasEstado.ASIGNADO;
    private static final TareasEstado UPDATED_ESTADO = TareasEstado.INICIADO;

    private static final Instant DEFAULT_FECHA_CREADO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREADO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_PREVISTO_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_PREVISTO_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_FINAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_FINAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_HORAS_PREVISTO = 1;
    private static final Integer UPDATED_HORAS_PREVISTO = 2;

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private TareaMapper tareaMapper;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTareaMockMvc;

    private Tarea tarea;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TareaResource tareaResource = new TareaResource(tareaService);
        this.restTareaMockMvc = MockMvcBuilders.standaloneSetup(tareaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarea createEntity(EntityManager em) {
        Tarea tarea = new Tarea()
            .descripcion(DEFAULT_DESCRIPCION)
            .estado(DEFAULT_ESTADO)
            .fechaCreado(DEFAULT_FECHA_CREADO)
            .fechaPrevistoInicio(DEFAULT_FECHA_PREVISTO_INICIO)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFinal(DEFAULT_FECHA_FINAL)
            .horasPrevisto(DEFAULT_HORAS_PREVISTO);
        return tarea;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarea createUpdatedEntity(EntityManager em) {
        Tarea tarea = new Tarea()
            .descripcion(UPDATED_DESCRIPCION)
            .estado(UPDATED_ESTADO)
            .fechaCreado(UPDATED_FECHA_CREADO)
            .fechaPrevistoInicio(UPDATED_FECHA_PREVISTO_INICIO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFinal(UPDATED_FECHA_FINAL)
            .horasPrevisto(UPDATED_HORAS_PREVISTO);
        return tarea;
    }

    @BeforeEach
    public void initTest() {
        tarea = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarea() throws Exception {
        int databaseSizeBeforeCreate = tareaRepository.findAll().size();

        // Create the Tarea
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);
        restTareaMockMvc.perform(post("/api/tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isCreated());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeCreate + 1);
        Tarea testTarea = tareaList.get(tareaList.size() - 1);
        assertThat(testTarea.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTarea.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testTarea.getFechaCreado()).isEqualTo(DEFAULT_FECHA_CREADO);
        assertThat(testTarea.getFechaPrevistoInicio()).isEqualTo(DEFAULT_FECHA_PREVISTO_INICIO);
        assertThat(testTarea.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testTarea.getFechaFinal()).isEqualTo(DEFAULT_FECHA_FINAL);
        assertThat(testTarea.getHorasPrevisto()).isEqualTo(DEFAULT_HORAS_PREVISTO);
    }

    @Test
    @Transactional
    public void createTareaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tareaRepository.findAll().size();

        // Create the Tarea with an existing ID
        tarea.setId(1L);
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTareaMockMvc.perform(post("/api/tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTareas() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList
        restTareaMockMvc.perform(get("/api/tareas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarea.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].fechaCreado").value(hasItem(DEFAULT_FECHA_CREADO.toString())))
            .andExpect(jsonPath("$.[*].fechaPrevistoInicio").value(hasItem(DEFAULT_FECHA_PREVISTO_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFinal").value(hasItem(DEFAULT_FECHA_FINAL.toString())))
            .andExpect(jsonPath("$.[*].horasPrevisto").value(hasItem(DEFAULT_HORAS_PREVISTO)));
    }
    
    @Test
    @Transactional
    public void getTarea() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get the tarea
        restTareaMockMvc.perform(get("/api/tareas/{id}", tarea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tarea.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.fechaCreado").value(DEFAULT_FECHA_CREADO.toString()))
            .andExpect(jsonPath("$.fechaPrevistoInicio").value(DEFAULT_FECHA_PREVISTO_INICIO.toString()))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFinal").value(DEFAULT_FECHA_FINAL.toString()))
            .andExpect(jsonPath("$.horasPrevisto").value(DEFAULT_HORAS_PREVISTO));
    }

    @Test
    @Transactional
    public void getNonExistingTarea() throws Exception {
        // Get the tarea
        restTareaMockMvc.perform(get("/api/tareas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarea() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        int databaseSizeBeforeUpdate = tareaRepository.findAll().size();

        // Update the tarea
        Tarea updatedTarea = tareaRepository.findById(tarea.getId()).get();
        // Disconnect from session so that the updates on updatedTarea are not directly saved in db
        em.detach(updatedTarea);
        updatedTarea
            .descripcion(UPDATED_DESCRIPCION)
            .estado(UPDATED_ESTADO)
            .fechaCreado(UPDATED_FECHA_CREADO)
            .fechaPrevistoInicio(UPDATED_FECHA_PREVISTO_INICIO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFinal(UPDATED_FECHA_FINAL)
            .horasPrevisto(UPDATED_HORAS_PREVISTO);
        TareaDTO tareaDTO = tareaMapper.toDto(updatedTarea);

        restTareaMockMvc.perform(put("/api/tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isOk());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeUpdate);
        Tarea testTarea = tareaList.get(tareaList.size() - 1);
        assertThat(testTarea.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTarea.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testTarea.getFechaCreado()).isEqualTo(UPDATED_FECHA_CREADO);
        assertThat(testTarea.getFechaPrevistoInicio()).isEqualTo(UPDATED_FECHA_PREVISTO_INICIO);
        assertThat(testTarea.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testTarea.getFechaFinal()).isEqualTo(UPDATED_FECHA_FINAL);
        assertThat(testTarea.getHorasPrevisto()).isEqualTo(UPDATED_HORAS_PREVISTO);
    }

    @Test
    @Transactional
    public void updateNonExistingTarea() throws Exception {
        int databaseSizeBeforeUpdate = tareaRepository.findAll().size();

        // Create the Tarea
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTareaMockMvc.perform(put("/api/tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarea() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        int databaseSizeBeforeDelete = tareaRepository.findAll().size();

        // Delete the tarea
        restTareaMockMvc.perform(delete("/api/tareas/{id}", tarea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tarea.class);
        Tarea tarea1 = new Tarea();
        tarea1.setId(1L);
        Tarea tarea2 = new Tarea();
        tarea2.setId(tarea1.getId());
        assertThat(tarea1).isEqualTo(tarea2);
        tarea2.setId(2L);
        assertThat(tarea1).isNotEqualTo(tarea2);
        tarea1.setId(null);
        assertThat(tarea1).isNotEqualTo(tarea2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TareaDTO.class);
        TareaDTO tareaDTO1 = new TareaDTO();
        tareaDTO1.setId(1L);
        TareaDTO tareaDTO2 = new TareaDTO();
        assertThat(tareaDTO1).isNotEqualTo(tareaDTO2);
        tareaDTO2.setId(tareaDTO1.getId());
        assertThat(tareaDTO1).isEqualTo(tareaDTO2);
        tareaDTO2.setId(2L);
        assertThat(tareaDTO1).isNotEqualTo(tareaDTO2);
        tareaDTO1.setId(null);
        assertThat(tareaDTO1).isNotEqualTo(tareaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tareaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tareaMapper.fromId(null)).isNull();
    }
}
