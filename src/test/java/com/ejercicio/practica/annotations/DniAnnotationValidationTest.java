package com.ejercicio.practica.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

/**
 * Integration-style tests proving that {@link Dni} is correctly wired to Bean Validation
 * (i.e., it triggers validation and uses {@link com.ejercicio.practica.validators.DniValidator}).
 *
 * <p>These tests are intentionally not testing annotation "methods" directly (message/groups/payload),
 * but rather the runtime behavior: applying @Dni produces constraint violations as expected.</p>
 */
class DniAnnotationValidationTest {

    private static Validator validator;

    private static class Dummy {

        @Dni
        private final String dni;

        private Dummy(String dni) {
            this.dni = dni;
        }
    }

    @BeforeAll
    static void setUpValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("@Dni produces no violations for a valid DNI")
    void dni_valid_noViolations() {
        Set<ConstraintViolation<Dummy>> violations = validator.validate(new Dummy("00000000T"));
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("@Dni produces a violation for an invalid-format DNI")
    void dni_invalidFormat_violationWithMessage() {
        Set<ConstraintViolation<Dummy>> violations = validator.validate(new Dummy("12345678a"));

        assertEquals(1, violations.size());
        assertEquals("Invalid DNI format", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("@Dni produces a violation for a wrong-checksum DNI")
    void dni_wrongLetter_violationWithMessage() {
        Set<ConstraintViolation<Dummy>> violations = validator.validate(new Dummy("00000000R"));

        assertEquals(1, violations.size());
        assertEquals("Invalid DNI format", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("@Dni produces a violation for null (as per DniValidator behavior)")
    void dni_null_violationWithMessage() {
        Set<ConstraintViolation<Dummy>> violations = validator.validate(new Dummy(null));

        assertEquals(1, violations.size());
        assertEquals("Invalid DNI format", violations.iterator().next().getMessage());
    }
}
