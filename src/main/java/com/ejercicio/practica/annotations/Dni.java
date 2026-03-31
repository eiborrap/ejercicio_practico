package com.ejercicio.practica.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import com.ejercicio.practica.validators.DniValidator;

/**
 * Bean Validation constraint for Spanish DNI values.
 *
 * <p>Accepts the format {@code NNNNNNNNL} (8 digits followed by an uppercase letter) and validates
 * the control letter using {@link DniValidator}.</p>
 *
 * <p>Applicable to fields and method parameters.</p>
 */
@Documented
@Constraint(validatedBy = DniValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Dni {
    String message() default "Invalid DNI format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
