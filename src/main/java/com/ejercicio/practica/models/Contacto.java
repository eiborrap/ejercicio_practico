package com.ejercicio.practica.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_CONTACTS")
public class Contacto {
    @Column(name = "Telephone", nullable = false)
    private Integer telephone;

    @Column(name = "Street")
    private String street;

    @Column(name = "Email")
    private String email;

    
    @EmbeddedId
    private ContactoId id;

    @ManyToOne(optional = false)
    @MapsId("idPersona")
    @JoinColumn(name = "ID_PERSON", referencedColumnName = "ID_PERSON")
    private Persona personaById;

    @ManyToOne(optional = false)
    @MapsId("dniPersona")
    @JoinColumn(name = "DNI", referencedColumnName = "DNI")
    private Persona personaByDni;


}
