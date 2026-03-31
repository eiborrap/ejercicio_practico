package com.ejercicio.practica.validators;

import com.ejercicio.practica.annotations.Dni;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for the {@link Dni} constraint.
 *
 * <p>Validates Spanish DNI format {@code NNNNNNNNL} (8 digits followed by an uppercase letter)
 * and checks that the letter matches the number according to the official modulus 23 algorithm.</p>
 *
 * <p>Rules:</p>
 * <ul>
 *   <li>{@code null} values are considered invalid.</li>
 *   <li>Only DNI values matching the regex {@code ^[0-9]{8}[A-Z]$} are accepted.</li>
 *   <li>The control letter is computed from the numeric part modulo 23.</li>
 * </ul>
 */
public class DniValidator implements ConstraintValidator<Dni, String> {

    private static final String LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";

    @Override
    public boolean isValid(String dni, ConstraintValidatorContext context) {
        if (dni == null || !dni.matches("^[0-9]{8}[A-Z]$")) {
            return false;
        }

        int number = Integer.parseInt(dni.substring(0, 8));
        char expectedLetter = LETTERS.charAt(number % 23);
        char actualLetter = dni.charAt(8);

        return expectedLetter == actualLetter;
    }
}
