package com.ejercicio.practica.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
import com.ejercicio.practica.validators.DniValidator;

@Documented
@Constraint(validatedBy = DniValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Dni {
    String message() default "Invalid DNI format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}