package com.ejercicio.practica.validators;

import com.ejercicio.practica.annotations.Dni;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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