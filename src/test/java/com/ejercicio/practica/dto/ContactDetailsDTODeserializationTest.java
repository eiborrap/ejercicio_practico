package com.ejercicio.practica.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ejercicio.practica.dtos.ContactDetailsDTO;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.exc.ValueInstantiationException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ContactDetailsDTO} Jackson deserialization using its Builder-based configuration:
 * {@code @JsonDeserialize(builder = ContactDetailsDTO.Builder.class)} and {@code @JsonPOJOBuilder(withPrefix="set")}.
 */
class ContactDetailsDTODeserializationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldDeserializeAllFields() throws Exception {
        String json = """
            {
              "telephone": 612345678,
              "street": "Calle Mayor 1",
              "email": "a@b.com"
            }
            """;

        ContactDetailsDTO dto = objectMapper.readValue(json, ContactDetailsDTO.class);

        assertEquals(612345678, dto.getTelephone());
        assertEquals("Calle Mayor 1", dto.getStreet());
        assertEquals("a@b.com", dto.getEmail());
    }

    @Test
    void shouldDeserializeWithOnlyMandatoryTelephoneAndLeaveOptionalsNull() throws Exception {
        String json = """
            {
              "telephone": 612345678
            }
            """;

        ContactDetailsDTO dto = objectMapper.readValue(json, ContactDetailsDTO.class);

        assertEquals(612345678, dto.getTelephone());
        assertNull(dto.getStreet());
        assertNull(dto.getEmail());
    }

    @Test
    void shouldFailDeserializationWhenTelephoneIsMissing() {
        String json = """
            {
              "street": "Calle Mayor 1",
              "email": "a@b.com"
            }
            """;

        // Builder has only Builder(Integer telephone) constructor, so without telephone JSON
        // Jackson can't create the builder and should throw.
        assertThrows(ValueInstantiationException.class, () -> objectMapper.readValue(json, ContactDetailsDTO.class));
    }

    @Test
    void shouldFailDeserializationWhenTelephoneIsExplicitNull() {
        String json = """
            {
              "telephone": null,
              "street": "Calle Mayor 1",
              "email": "a@b.com"
            }
            """;

        // Builder throws IllegalArgumentException when telephone == null; Jackson should wrap it.
        assertThrows(ValueInstantiationException.class, () -> objectMapper.readValue(json, ContactDetailsDTO.class));
    }
}
