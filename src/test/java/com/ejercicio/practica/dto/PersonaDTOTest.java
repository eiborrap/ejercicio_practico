package com.ejercicio.practica.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ejercicio.practica.dtos.ContactDetailsDTO;
import com.ejercicio.practica.dtos.PersonaDTO;

class PersonaDTOTest {

    private static ContactDetailsDTO validContactDetails() {
        return new ContactDetailsDTO.Builder()
                .setTelephone(600123123)
                .setStreet("Calle Falsa 123")
                .setEmail("john.doe@test.com")
                .build();
    }

    @Test
    @DisplayName("Builder: build ok populates fields and getters return values")
    void build_ok_and_getters() {
        ContactDetailsDTO contact = validContactDetails();

        PersonaDTO dto = new PersonaDTO.Builder()
                .setIdPerson(10)
                .setName("John")
                .setLastName("Doe")
                .setFullName("John Doe")
                .setDni("12345678Z")
                .setContactDetails(contact)
                .build();

        assertAll(
                () -> assertEquals(10, dto.getIdPerson()),
                () -> assertEquals("John", dto.getName()),
                () -> assertEquals("Doe", dto.getLastName()),
                () -> assertEquals("John Doe", dto.getFullName()),
                () -> assertEquals("12345678Z", dto.getDni()),
                () -> assertSame(contact, dto.getContactDetails())
        );
    }

    @Test
    @DisplayName("Builder: name null throws")
    void build_nameNull_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new PersonaDTO.Builder()
                .setName(null)
                .setLastName("Doe")
                .setDni("12345678Z")
                .setContactDetails(validContactDetails())
                .build());

        assertEquals("name is mandatory", ex.getMessage());
    }

    @Test
    @DisplayName("Builder: name blank throws")
    void build_nameBlank_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new PersonaDTO.Builder()
                .setName("   ")
                .setLastName("Doe")
                .setDni("12345678Z")
                .setContactDetails(validContactDetails())
                .build());

        assertEquals("name is mandatory", ex.getMessage());
    }

    @Test
    @DisplayName("Builder: lastName null throws")
    void build_lastNameNull_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new PersonaDTO.Builder()
                .setName("John")
                .setLastName(null)
                .setDni("12345678Z")
                .setContactDetails(validContactDetails())
                .build());

        assertEquals("lastName is mandatory", ex.getMessage());
    }

    @Test
    @DisplayName("Builder: lastName blank throws")
    void build_lastNameBlank_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new PersonaDTO.Builder()
                .setName("John")
                .setLastName("\t")
                .setDni("12345678Z")
                .setContactDetails(validContactDetails())
                .build());

        assertEquals("lastName is mandatory", ex.getMessage());
    }

    @Test
    @DisplayName("Builder: dni null throws")
    void build_dniNull_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new PersonaDTO.Builder()
                .setName("John")
                .setLastName("Doe")
                .setDni(null)
                .setContactDetails(validContactDetails())
                .build());

        assertEquals("dni is mandatory", ex.getMessage());
    }

    @Test
    @DisplayName("Builder: dni blank throws")
    void build_dniBlank_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new PersonaDTO.Builder()
                .setName("John")
                .setLastName("Doe")
                .setDni(" ")
                .setContactDetails(validContactDetails())
                .build());

        assertEquals("dni is mandatory", ex.getMessage());
    }

    @Test
    @DisplayName("Builder: contactDetails null throws")
    void build_contactDetailsNull_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new PersonaDTO.Builder()
                .setName("John")
                .setLastName("Doe")
                .setDni("12345678Z")
                .setContactDetails(null)
                .build());

        assertEquals("contactDetails is mandatory", ex.getMessage());
    }

    @Test
    @DisplayName("toString: JSON style contains expected keys and values (including DNI key)")
    void toString_containsExpectedFields() {
        ContactDetailsDTO contact = validContactDetails();

        PersonaDTO dto = new PersonaDTO.Builder()
                .setIdPerson(7)
                .setName("Jane")
                .setLastName("Roe")
                .setFullName("Jane Roe")
                .setDni("87654321X")
                .setContactDetails(contact)
                .build();

        String s = dto.toString();

        assertAll(
                () -> assertNotNull(s),
                () -> assertTrue(s.contains("\"idPerson\":7"), s),
                () -> assertTrue(s.contains("\"name\":\"Jane\""), s),
                () -> assertTrue(s.contains("\"lastName\":\"Roe\""), s),
                () -> assertTrue(s.contains("\"fullName\":\"Jane Roe\""), s),
                // Note: PersonaDTO uses key "DNI" (uppercase) in toString()
                () -> assertTrue(s.contains("\"DNI\":\"87654321X\""), s),
                () -> assertTrue(s.contains("\"contactDetails\":"), s)
        );
    }
}
