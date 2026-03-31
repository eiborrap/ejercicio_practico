package com.ejercicio.practica.mappers;

import com.ejercicio.practica.dtos.ContactDetailsDTO;
import com.ejercicio.practica.dtos.PersonaDTO;
import com.ejercicio.practica.models.Contacto;
import com.ejercicio.practica.models.Persona;

/**
 * Utility class containing mapping helpers between persistence entities and API DTOs.
 *
 * <p>Centralizes the conversion logic between:</p>
 * <ul>
 *   <li>{@link PersonaDTO} <-> {@link Persona}</li>
 *   <li>{@link ContactDetailsDTO} <-> {@link Contacto}</li>
 * </ul>
 *
 * <p>This class is stateless and cannot be instantiated.</p>
 */
public final class Mapper {

    private Mapper() {
    }

    /**
     * Maps a {@link PersonaDTO} into a {@link Persona} entity.
     *
     * <p>The returned {@link Persona} will also contain a {@link Contacto} entity built from
     * {@link PersonaDTO#getContactDetails()} and associated back to the created {@link Persona}.</p>
     *
     * @param dto source DTO
     * @return mapped entity
     */
    public static Persona toPersona(PersonaDTO dto) {

        ContactDetailsDTO contactDetails = dto.getContactDetails();
        Persona p = new Persona(
            dto.getIdPerson(), 
            dto.getName(), 
            dto.getLastName(), 
            dto.getDni()
            );
        p.setContacto(new Contacto(
                contactDetails.getTelephone(),
                contactDetails.getStreet(),
                contactDetails.getEmail(),
                p));

        return p;

    }

    /**
     * Maps a {@link Persona} entity into a {@link PersonaDTO}.
     *
     * <p>Creates {@code fullName} as {@code "name lastname"} and maps contact information
     * from {@link Persona#getContacto()}.</p>
     *
     * @param persona source entity
     * @return mapped DTO
     */
    public static PersonaDTO toPersonaDTO(Persona persona) {
        ContactDetailsDTO contactDetails = toContactDetailsDTO(persona.getContacto());

        return new PersonaDTO.Builder()
                .setName(persona.getName())
                .setLastName(persona.getLastname())
                .setDni(persona.getDni())
                .setIdPerson(persona.getId())
                .setFullName(persona.getName() + " " + persona.getLastname())
                .setContactDetails(contactDetails)
                .build();
    }

    /**
     * Maps a {@link Contacto} entity into a {@link ContactDetailsDTO}.
     *
     * @param c source contact entity
     * @return mapped DTO
     */
    private static ContactDetailsDTO toContactDetailsDTO(Contacto c){
        return new ContactDetailsDTO.Builder()
                .setTelephone(c.getTelephone())
                .setStreet(c.getStreet())
                .setEmail(c.getEmail())
                .build();
    }
}
