package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ContactoService;
import io.github.jhipster.application.domain.Contacto;
import io.github.jhipster.application.repository.ContactoRepository;
import io.github.jhipster.application.service.dto.ContactoDTO;
import io.github.jhipster.application.service.mapper.ContactoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Contacto}.
 */
@Service
@Transactional
public class ContactoServiceImpl implements ContactoService {

    private final Logger log = LoggerFactory.getLogger(ContactoServiceImpl.class);

    private final ContactoRepository contactoRepository;

    private final ContactoMapper contactoMapper;

    public ContactoServiceImpl(ContactoRepository contactoRepository, ContactoMapper contactoMapper) {
        this.contactoRepository = contactoRepository;
        this.contactoMapper = contactoMapper;
    }

    /**
     * Save a contacto.
     *
     * @param contactoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ContactoDTO save(ContactoDTO contactoDTO) {
        log.debug("Request to save Contacto : {}", contactoDTO);
        Contacto contacto = contactoMapper.toEntity(contactoDTO);
        contacto = contactoRepository.save(contacto);
        return contactoMapper.toDto(contacto);
    }

    /**
     * Get all the contactos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContactoDTO> findAll() {
        log.debug("Request to get all Contactos");
        return contactoRepository.findAll().stream()
            .map(contactoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one contacto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContactoDTO> findOne(Long id) {
        log.debug("Request to get Contacto : {}", id);
        return contactoRepository.findById(id)
            .map(contactoMapper::toDto);
    }

    /**
     * Delete the contacto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contacto : {}", id);
        contactoRepository.deleteById(id);
    }
}
