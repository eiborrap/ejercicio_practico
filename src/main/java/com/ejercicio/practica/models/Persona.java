package com.ejercicio.practica.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "T_PERSONS")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERSON")
    private int id;


    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "LASTNAME", nullable = false)
    private String lastname;

     //It need to be unique to be a foreign key
    @Column(name = "DNI", nullable = false, unique = true) 
    private String dni;
    

    
}
