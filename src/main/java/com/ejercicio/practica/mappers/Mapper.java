package com.ejercicio.practica.mappers;

import com.ejercicio.practica.dtos.ContactDetailsDTO;
import com.ejercicio.practica.dtos.PersonaDTO;
import com.ejercicio.practica.models.Contacto;
import com.ejercicio.practica.models.Persona;

public final class Mapper {

    private Mapper() {
    }

    /**
     * DTO -> Entity.
     *
     * <p>
     * If the DTO has no id, we keep id=0 so JPA {@code @GeneratedValue} can assign
     * it on insert.
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
     * Entity -> DTO. ContactDetails are mandatory in {@link PersonaDTO}, so this
     * method requires a {@link Contacto}. Use the overload with Optional if you
     * might not have it.
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

    private static ContactDetailsDTO toContactDetailsDTO(Contacto c){
        return new ContactDetailsDTO.Builder()
                .setTelephone(c.getTelephone())
                .setStreet(c.getStreet())
                .setEmail(c.getEmail())
                .build();
    }
}
