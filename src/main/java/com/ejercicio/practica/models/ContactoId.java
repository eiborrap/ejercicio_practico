package com.ejercicio.practica.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ContactoId{
    @Column(name = "ID_PERSON", nullable = false)
    private int idPersona;

    @Column(name = "DNI", nullable = false)
    private int dniPersona;

}