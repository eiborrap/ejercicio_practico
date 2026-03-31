package com.ejercicio.practica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejercicio.practica.models.Persona;

/**
 * Spring Data JPA repository for {@link Persona} entities.
 *
 * <p>Extends {@link JpaRepository} to provide basic CRUD operations and declares a couple of
 * query methods derived from method names (e.g. {@link #getByDni(String)}).</p>
 */
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    /**
     * Retrieves a {@link Persona} by its primary key.
     *
     * @param id primary key ({@code ID_PERSON})
     * @return the matching person, or {@code null} if not found (depending on Spring Data behavior)
     */
    Persona getById(Integer id);

    /**
     * Retrieves a {@link Persona} by its DNI.
     *
     * @param dni unique DNI
     * @return the matching person, or {@code null} if not found
     */
    Persona getByDni(String dni);

    /**
     * Deletes a {@link Persona} by its primary key.
     *
     * @param id primary key ({@code ID_PERSON})
     */
    void deleteById(Integer id);

    /**
     * Deletes all {@link Persona} records.
     */
    void deleteAll();
}
