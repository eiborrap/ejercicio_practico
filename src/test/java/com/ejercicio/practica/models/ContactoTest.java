package com.ejercicio.practica.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Contacto} focused on exercising all constructors,
 * getters and setters to reach 100% line coverage.
 */
class ContactoTest {

    private static Persona persona(Integer id, String dni) {
        return new Persona(id, "Name" + id, "Last" + id, dni);
    }

    @Test
    void defaultConstructor_allowsSettingAndGettingAllFields() {
        Contacto contacto = new Contacto();

        // initial state (constructor line coverage + null defaults)
        assertNull(contacto.getPersona());
        assertNull(contacto.getPersonaByDni());
        assertNull(contacto.getTelephone());
        assertNull(contacto.getStreet());
        assertNull(contacto.getEmail());

        Persona p1 = persona(1, "11111111A");
        Persona p2 = persona(2, "22222222B");

        contacto.setId(99);
        contacto.setPersona(p1);
        contacto.setTelephone(600700800);
        contacto.setStreet("Main St");
        contacto.setEmail("a@b.com");
        contacto.setPersonaByDni(p2);

        assertSame(p1, contacto.getPersona());
        assertSame(p2, contacto.getPersonaByDni());
        assertEquals(600700800, contacto.getTelephone());
        assertEquals("Main St", contacto.getStreet());
        assertEquals("a@b.com", contacto.getEmail());
    }

    @Test
    void constructorWithPersona_setsPersonaAndPersonaByDniToSameInstance() {
        Persona p = persona(10, "33333333C");

        Contacto contacto = new Contacto(12345, "Calle 1", "x@y.com", p);

        assertEquals(12345, contacto.getTelephone());
        assertEquals("Calle 1", contacto.getStreet());
        assertEquals("x@y.com", contacto.getEmail());
        assertSame(p, contacto.getPersona());
        // per implementation: personaByDni defaults to persona in this constructor
        assertSame(p, contacto.getPersonaByDni());
    }

    @Test
    void constructorWithPersonaAndPersonaByDni_setsBothAssociationsIndependently() {
        Persona p = persona(11, "44444444D");
        Persona pByDni = persona(12, "55555555E");

        Contacto contacto = new Contacto(98765, null, null, p, pByDni);

        assertEquals(98765, contacto.getTelephone());
        assertNull(contacto.getStreet());
        assertNull(contacto.getEmail());
        assertSame(p, contacto.getPersona());
        assertSame(pByDni, contacto.getPersonaByDni());
    }
}
