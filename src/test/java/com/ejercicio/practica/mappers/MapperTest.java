package com.ejercicio.practica.mappers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ejercicio.practica.dtos.ContactDetailsDTO;
import com.ejercicio.practica.dtos.PersonaDTO;
import com.ejercicio.practica.models.Contacto;
import com.ejercicio.practica.models.Persona;

class MapperTest {

    @Test
    void toPersona_mapsAllPersonaFields_andCreatesContactoWithBackReference() {
        ContactDetailsDTO contactDetails = new ContactDetailsDTO.Builder()
                .setTelephone(666777888)
                .setStreet("Calle Mayor 1")
                .setEmail("john.doe@example.com")
                .build();

        PersonaDTO dto = new PersonaDTO.Builder()
                .setIdPerson(10)
                .setName("John")
                .setLastName("Doe")
                .setDni("12345678Z")
                .setContactDetails(contactDetails)
                .build();

        Persona persona = Mapper.toPersona(dto);

        assertNotNull(persona);
        assertEquals(10, persona.getId());
        assertEquals("John", persona.getName());
        assertEquals("Doe", persona.getLastname());
        assertEquals("12345678Z", persona.getDni());

        assertNotNull(persona.getContacto());

        Contacto contacto = persona.getContacto();
        assertEquals(666777888, contacto.getTelephone());
        assertEquals("Calle Mayor 1", contacto.getStreet());
        assertEquals("john.doe@example.com", contacto.getEmail());

        // Critical for JPA mapping: Contacto must point back to the same Persona instance
        assertSame(persona, contacto.getPersona());
        assertSame(persona, contacto.getPersonaByDni());
    }

    @Test
    void toPersonaDTO_mapsAllFields_buildsFullName_andMapsNestedContactDetails() {
        Persona persona = new Persona(20, "Jane", "Smith", "87654321X");
        Contacto contacto = new Contacto(123456789, "Gran Via 2", "jane.smith@example.com", persona);
        persona.setContacto(contacto);

        PersonaDTO dto = Mapper.toPersonaDTO(persona);

        assertNotNull(dto);
        assertEquals(20, dto.getIdPerson());
        assertEquals("Jane", dto.getName());
        assertEquals("Smith", dto.getLastName());
        assertEquals("87654321X", dto.getDni());
        assertEquals("Jane Smith", dto.getFullName());

        assertNotNull(dto.getContactDetails());
        assertEquals(123456789, dto.getContactDetails().getTelephone());
        assertEquals("Gran Via 2", dto.getContactDetails().getStreet());
        assertEquals("jane.smith@example.com", dto.getContactDetails().getEmail());
    }

    @Test
    void toPersonaDTO_throwsNullPointerException_whenContactoIsNull() {
        Persona persona = new Persona(1, "No", "Contact", "00000000T");
        // contacto intentionally left null

        assertThrows(NullPointerException.class, () -> Mapper.toPersonaDTO(persona));
    }
}
