package com.ejercicio.practica.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "T_PERSONS")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERSON")
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "LASTNAME", nullable = false)
    private String lastname;

     //It need to be unique to be a foreign key
    @Column(name = "DNI", nullable = false, unique = true) 
    private String dni;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    private Contacto contacto;

    
    public Persona() {
    }

    public Persona(Integer id, String name, String lastname, String dni, Contacto contacto) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.contacto = contacto;
    }
    public Persona(Integer id, String name, String lastname, String dni){
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.contacto = null;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }
    

    
}
