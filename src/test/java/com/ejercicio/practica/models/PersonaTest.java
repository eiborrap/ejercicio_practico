package com.ejercicio.practica.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Persona} focused on exercising all constructors,
 * getters and setters to reach 100% line coverage.
 */
class PersonaTest {

    @Test
    void defaultConstructor_allowsSettingAndGettingAllFields() {
        Persona persona = new Persona();

        // defaults
        assertNull(persona.getId());
        assertNull(persona.getName());
        assertNull(persona.getLastname());
        assertNull(persona.getDni());
        assertNull(persona.getContacto());

        Contacto contacto = new Contacto();
        persona.setId(1);
        persona.setName("Ada");
        persona.setLastname("Lovelace");
        persona.setDni("12345678Z");
        persona.setContacto(contacto);

        assertEquals(1, persona.getId());
        assertEquals("Ada", persona.getName());
        assertEquals("Lovelace", persona.getLastname());
        assertEquals("12345678Z", persona.getDni());
        assertSame(contacto, persona.getContacto());
    }

    @Test
    void constructorWithContacto_setsAllFields() {
        Contacto contacto = new Contacto();
        Persona persona = new Persona(10, "John", "Doe", "11111111A", contacto);

        assertEquals(10, persona.getId());
        assertEquals("John", persona.getName());
        assertEquals("Doe", persona.getLastname());
        assertEquals("11111111A", persona.getDni());
        assertSame(contacto, persona.getContacto());
    }

    @Test
    void constructorWithoutContacto_setsContactoToNull() {
        Persona persona = new Persona(11, "Jane", "Roe", "22222222B");

        assertEquals(11, persona.getId());
        assertEquals("Jane", persona.getName());
        assertEquals("Roe", persona.getLastname());
        assertEquals("22222222B", persona.getDni());
        assertNull(persona.getContacto());
    }
}
