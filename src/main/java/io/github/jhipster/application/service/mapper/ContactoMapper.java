package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ContactoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contacto} and its DTO {@link ContactoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface ContactoMapper extends EntityMapper<ContactoDTO, Contacto> {

    @Mapping(source = "cliente.id", target = "clienteId")
    ContactoDTO toDto(Contacto contacto);

    @Mapping(source = "clienteId", target = "cliente")
    Contacto toEntity(ContactoDTO contactoDTO);

    default Contacto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contacto contacto = new Contacto();
        contacto.setId(id);
        return contacto;
    }
}
