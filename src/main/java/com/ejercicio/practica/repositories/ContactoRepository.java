package com.ejercicio.practica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejercicio.practica.models.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {
    // If needed:
    // Optional<Contacto> findByPersonaId(Integer id);
}
