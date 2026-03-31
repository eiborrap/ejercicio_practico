package com.ejercicio.practica.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link DniValidator}.
 *
 * <p>Goal: 100% line/branch coverage of {@link DniValidator#isValid(String,
 * jakarta.validation.ConstraintValidatorContext)}.
 */
class DniValidatorTest {

    private final DniValidator validator = new DniValidator();

    @Test
    @DisplayName("isValid returns false when dni is null")
    void isValid_null_returnsFalse() {
        assertFalse(validator.isValid(null, null));
    }

    @Test
    @DisplayName("isValid returns false when dni doesn't match the required format: 8 digits + uppercase letter")
    void isValid_invalidFormat_returnsFalse() {
        // too short
        assertFalse(validator.isValid("1234567A", null));
        // lowercase char
        assertFalse(validator.isValid("12345678a", null));
        // invalid length (extra char)
        assertFalse(validator.isValid("12345678AA", null));
        // non-digit in numeric part
        assertFalse(validator.isValid("1234A678Z", null));
    }

    @Test
    @DisplayName("isValid returns false when dni has correct format but incorrect checksum letter")
    void isValid_wrongLetter_returnsFalse() {
        // For 00000000, the expected letter is 'T'
        assertFalse(validator.isValid("00000000R", null));
    }

    @Test
    @DisplayName("isValid returns true when dni has correct format and correct checksum letter")
    void isValid_validDni_returnsTrue() {
        // 00000000 -> expected 'T'
        assertTrue(validator.isValid("00000000T", null));

        // Another deterministic example:
        // 12345678 % 23 = 14 -> LETTERS[14] = 'Z'
        assertTrue(validator.isValid("12345678Z", null));
    }
}
