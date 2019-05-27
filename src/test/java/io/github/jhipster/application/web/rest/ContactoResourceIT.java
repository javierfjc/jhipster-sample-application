package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhispterOrclApp;
import io.github.jhipster.application.domain.Contacto;
import io.github.jhipster.application.repository.ContactoRepository;
import io.github.jhipster.application.service.ContactoService;
import io.github.jhipster.application.service.dto.ContactoDTO;
import io.github.jhipster.application.service.mapper.ContactoMapper;
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
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ContactoResource} REST controller.
 */
@SpringBootTest(classes = JhispterOrclApp.class)
public class ContactoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_PERFIL = "AAAAAAAAAA";
    private static final String UPDATED_PERFIL = "BBBBBBBBBB";

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private ContactoMapper contactoMapper;

    @Autowired
    private ContactoService contactoService;

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

    private MockMvc restContactoMockMvc;

    private Contacto contacto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactoResource contactoResource = new ContactoResource(contactoService);
        this.restContactoMockMvc = MockMvcBuilders.standaloneSetup(contactoResource)
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
    public static Contacto createEntity(EntityManager em) {
        Contacto contacto = new Contacto()
            .nombre(DEFAULT_NOMBRE)
            .email(DEFAULT_EMAIL)
            .telefono(DEFAULT_TELEFONO)
            .perfil(DEFAULT_PERFIL);
        return contacto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contacto createUpdatedEntity(EntityManager em) {
        Contacto contacto = new Contacto()
            .nombre(UPDATED_NOMBRE)
            .email(UPDATED_EMAIL)
            .telefono(UPDATED_TELEFONO)
            .perfil(UPDATED_PERFIL);
        return contacto;
    }

    @BeforeEach
    public void initTest() {
        contacto = createEntity(em);
    }

    @Test
    @Transactional
    public void createContacto() throws Exception {
        int databaseSizeBeforeCreate = contactoRepository.findAll().size();

        // Create the Contacto
        ContactoDTO contactoDTO = contactoMapper.toDto(contacto);
        restContactoMockMvc.perform(post("/api/contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoDTO)))
            .andExpect(status().isCreated());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeCreate + 1);
        Contacto testContacto = contactoList.get(contactoList.size() - 1);
        assertThat(testContacto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testContacto.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContacto.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testContacto.getPerfil()).isEqualTo(DEFAULT_PERFIL);
    }

    @Test
    @Transactional
    public void createContactoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactoRepository.findAll().size();

        // Create the Contacto with an existing ID
        contacto.setId(1L);
        ContactoDTO contactoDTO = contactoMapper.toDto(contacto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactoMockMvc.perform(post("/api/contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContactos() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);

        // Get all the contactoList
        restContactoMockMvc.perform(get("/api/contactos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contacto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].perfil").value(hasItem(DEFAULT_PERFIL.toString())));
    }
    
    @Test
    @Transactional
    public void getContacto() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);

        // Get the contacto
        restContactoMockMvc.perform(get("/api/contactos/{id}", contacto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contacto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.perfil").value(DEFAULT_PERFIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContacto() throws Exception {
        // Get the contacto
        restContactoMockMvc.perform(get("/api/contactos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContacto() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);

        int databaseSizeBeforeUpdate = contactoRepository.findAll().size();

        // Update the contacto
        Contacto updatedContacto = contactoRepository.findById(contacto.getId()).get();
        // Disconnect from session so that the updates on updatedContacto are not directly saved in db
        em.detach(updatedContacto);
        updatedContacto
            .nombre(UPDATED_NOMBRE)
            .email(UPDATED_EMAIL)
            .telefono(UPDATED_TELEFONO)
            .perfil(UPDATED_PERFIL);
        ContactoDTO contactoDTO = contactoMapper.toDto(updatedContacto);

        restContactoMockMvc.perform(put("/api/contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoDTO)))
            .andExpect(status().isOk());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeUpdate);
        Contacto testContacto = contactoList.get(contactoList.size() - 1);
        assertThat(testContacto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testContacto.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContacto.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testContacto.getPerfil()).isEqualTo(UPDATED_PERFIL);
    }

    @Test
    @Transactional
    public void updateNonExistingContacto() throws Exception {
        int databaseSizeBeforeUpdate = contactoRepository.findAll().size();

        // Create the Contacto
        ContactoDTO contactoDTO = contactoMapper.toDto(contacto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactoMockMvc.perform(put("/api/contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContacto() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);

        int databaseSizeBeforeDelete = contactoRepository.findAll().size();

        // Delete the contacto
        restContactoMockMvc.perform(delete("/api/contactos/{id}", contacto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contacto.class);
        Contacto contacto1 = new Contacto();
        contacto1.setId(1L);
        Contacto contacto2 = new Contacto();
        contacto2.setId(contacto1.getId());
        assertThat(contacto1).isEqualTo(contacto2);
        contacto2.setId(2L);
        assertThat(contacto1).isNotEqualTo(contacto2);
        contacto1.setId(null);
        assertThat(contacto1).isNotEqualTo(contacto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactoDTO.class);
        ContactoDTO contactoDTO1 = new ContactoDTO();
        contactoDTO1.setId(1L);
        ContactoDTO contactoDTO2 = new ContactoDTO();
        assertThat(contactoDTO1).isNotEqualTo(contactoDTO2);
        contactoDTO2.setId(contactoDTO1.getId());
        assertThat(contactoDTO1).isEqualTo(contactoDTO2);
        contactoDTO2.setId(2L);
        assertThat(contactoDTO1).isNotEqualTo(contactoDTO2);
        contactoDTO1.setId(null);
        assertThat(contactoDTO1).isNotEqualTo(contactoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contactoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contactoMapper.fromId(null)).isNull();
    }
}
