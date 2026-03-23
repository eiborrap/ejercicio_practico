package com.ejercicio.practica.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ejercicio.practica.dtos.PersonaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PersonaDTO} Jackson deserialization using its Builder-based configuration:
 * {@code @JsonDeserialize(builder = PersonaDTO.Builder.class)} and {@code @JsonPOJOBuilder(withPrefix="set")}.
 *
 * PersonaDTO.Builder constructor requires: name, lastName, DNI, contactDetails.
 */
class PersonaDTODeserializationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldDeserializeMandatoryFieldsAndNestedContactDetails() throws Exception {
        String json = """
            {
              "name": "Ana",
              "lastName": "García",
              "DNI": "12345678A",
              "contactDetails": {
                "telephone": 612345678,
                "street": "Calle Mayor 1",
                "email": "ana@example.com"
              }
            }
            """;

        PersonaDTO dto = objectMapper.readValue(json, PersonaDTO.class);

        assertNull(dto.getIdPerson());
        assertEquals("Ana", dto.getName());
        assertEquals("García", dto.getLastName());
        assertNull(dto.getFullName());
        assertEquals("12345678A", dto.getDNI());

        assertEquals(612345678, dto.getContactDetails().getTelephone());
        assertEquals("Calle Mayor 1", dto.getContactDetails().getStreet());
        assertEquals("ana@example.com", dto.getContactDetails().getEmail());
    }

    @Test
    void shouldDeserializeOptionalFieldsSetters_idPersonAndFullName() throws Exception {
        String json = """
            {
              "idPerson": 10,
              "fullName": "Ana García",
              "name": "Ana",
              "lastName": "García",
              "DNI": "12345678A",
              "contactDetails": {
                "telephone": 600000000
              }
            }
            """;

        PersonaDTO dto = objectMapper.readValue(json, PersonaDTO.class);

        assertEquals(10, dto.getIdPerson());
        assertEquals("Ana", dto.getName());
        assertEquals("García", dto.getLastName());
        assertEquals("Ana García", dto.getFullName());
        assertEquals("12345678A", dto.getDNI());

        assertEquals(600000000, dto.getContactDetails().getTelephone());
        assertNull(dto.getContactDetails().getStreet());
        assertNull(dto.getContactDetails().getEmail());
    }

    @Test
    void shouldFailDeserializationWhenNameIsMissing() {
        String json = """
            {
              "lastName": "García",
              "DNI": "12345678A",
              "contactDetails": { "telephone": 612345678 }
            }
            """;

        // PersonaDTO.Builder requires name in constructor; without it Jackson can't create the builder.
        assertThrows(JsonProcessingException.class, () -> objectMapper.readValue(json, PersonaDTO.class));
    }

    @Test
    void shouldFailDeserializationWhenNameIsBlank() {
        String json = """
            {
              "name": "   ",
              "lastName": "García",
              "DNI": "12345678A",
              "contactDetails": { "telephone": 612345678 }
            }
            """;

        // Builder throws IllegalArgumentException when name is blank; Jackson should wrap it.
        assertThrows(JsonProcessingException.class, () -> objectMapper.readValue(json, PersonaDTO.class));
    }

    @Test
    void shouldFailDeserializationWhenLastNameIsMissing() {
        String json = """
            {
              "name": "Ana",
              "DNI": "12345678A",
              "contactDetails": { "telephone": 612345678 }
            }
            """;

        assertThrows(JsonProcessingException.class, () -> objectMapper.readValue(json, PersonaDTO.class));
    }

    @Test
    void shouldFailDeserializationWhenDniIsMissing() {
        String json = """
            {
              "name": "Ana",
              "lastName": "García",
              "contactDetails": { "telephone": 612345678 }
            }
            """;

        assertThrows(JsonProcessingException.class, () -> objectMapper.readValue(json, PersonaDTO.class));
    }

    @Test
    void shouldFailDeserializationWhenContactDetailsIsMissing() {
        String json = """
            {
              "name": "Ana",
              "lastName": "García",
              "DNI": "12345678A"
            }
            """;

        assertThrows(JsonProcessingException.class, () -> objectMapper.readValue(json, PersonaDTO.class));
    }

    @Test
    void shouldFailDeserializationWhenContactDetailsIsNull() {
        String json = """
            {
              "name": "Ana",
              "lastName": "García",
              "DNI": "12345678A",
              "contactDetails": null
            }
            """;

        assertThrows(JsonProcessingException.class, () -> objectMapper.readValue(json, PersonaDTO.class));
    }

    @Test
    void shouldFailDeserializationWhenNestedContactDetailsIsInvalid_missingTelephone() {
        String json = """
            {
              "name": "Ana",
              "lastName": "García",
              "DNI": "12345678A",
              "contactDetails": {
                "street": "Calle Mayor 1"
              }
            }
            """;

        assertThrows(JsonProcessingException.class, () -> objectMapper.readValue(json, PersonaDTO.class));
    }
}
