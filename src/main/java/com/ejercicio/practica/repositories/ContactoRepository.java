package com.ejercicio.practica.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejercicio.practica.models.Contacto;
import com.ejercicio.practica.models.Persona;

public interface ContactoRepository{// extends JpaRepository<Contacto, Persona> {
    //Optional<Contacto> findfindByPersona(int id);
}
