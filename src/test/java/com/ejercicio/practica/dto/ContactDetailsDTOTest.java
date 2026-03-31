package com.ejercicio.practica.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.ejercicio.practica.dtos.ContactDetailsDTO;

class ContactDetailsDTOTest {

    @Test
    void builderShouldCreateInstanceAndExposeGetters() {
        ContactDetailsDTO dto = new ContactDetailsDTO.Builder()
                .setTelephone(612345678)
                .setStreet("Calle Mayor 1")
                .setEmail("a@b.com")
                .build();

        assertNotNull(dto);
        assertEquals(612345678, dto.getTelephone());
        assertEquals("Calle Mayor 1", dto.getStreet());
        assertEquals("a@b.com", dto.getEmail());
    }

    @Test
    void builderShouldAllowNullOptionals() {
        ContactDetailsDTO dto = new ContactDetailsDTO.Builder()
                .setTelephone(600000000)
                .build();

        assertEquals(600000000, dto.getTelephone());
        assertNull(dto.getStreet());
        assertNull(dto.getEmail());
    }

    @Test
    void builderShouldThrowWhenTelephoneIsMissing() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new ContactDetailsDTO.Builder().build());

        assertEquals("telephone is mandatory", ex.getMessage());
    }

    @Test
    void toStringShouldMatchExpectedJsonStyle() {
        ContactDetailsDTO dto = new ContactDetailsDTO.Builder()
                .setTelephone(612345678)
                .setStreet("Calle Mayor 1")
                .setEmail("a@b.com")
                .build();

        // ToStringBuilder with JSON_STYLE uses this exact format/order for the append calls in ContactDetailsDTO.
        assertEquals(
                "{\"telephone\":612345678,\"street\":\"Calle Mayor 1\",\"email\":\"a@b.com\"}",
                dto.toString()
        );
    }

    @Test
    void toStringShouldIncludeNullsWhenOptionalsAreNull() {
        ContactDetailsDTO dto = new ContactDetailsDTO.Builder()
                .setTelephone(600000000)
                .build();

        assertEquals(
                "{\"telephone\":600000000,\"street\":null,\"email\":null}",
                dto.toString()
        );
    }
}
