package com.ejercicio.practica.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * JPA entity representing a person's contact information.
 *
 * <p>Maps to table {@code T_CONTACTS}. The entity uses a shared primary key
 * association with {@link Persona}:</p>
 * <ul>
 *   <li>{@code id} is both the primary key of {@code T_CONTACTS} and a foreign key to
 *       {@code T_PERSONS.ID_PERSON} (see {@link MapsId}).</li>
 *   <li>{@code persona} is the mandatory one-to-one association using the shared PK.</li>
 *   <li>{@code personaByDni} is an additional association using {@code DNI} as a foreign key
 *       (see {@link JoinColumn}), pointing to {@code T_PERSONS.DNI}. It is here due to Table design
 *       requirements.</li>
 * </ul>
 *
 * <p>Field constraints:</p>
 * <ul>
 *   <li>{@code telephone} is mandatory (non-null).</li>
 *   <li>{@code street} and {@code email} are optional.</li>
 * </ul>
 */
@Entity
@Table(name = "T_CONTACTS")
public class Contacto {

    @Id
    @Column(name = "ID_PERSON")
    private Integer id;  // PK of Contacto; same value as Persona.id

    @OneToOne(optional = false)
    @MapsId  // <-- ties this association to the PK field above
    @JoinColumn(name = "ID_PERSON", referencedColumnName = "ID_PERSON")
    private Persona persona;


    @Column(name = "Telephone", nullable = false)
    private Integer telephone;

    @Column(name = "Street")
    private String street;

    @Column(name = "Email")
    private String email;

    /**
     * Additional foreign key by DNI:
     * T_CONTACTS.DNI -> T_PERSONS.DNI (unique in Persona).
     */
    @OneToOne(optional = false)
    @JoinColumn(name = "DNI", referencedColumnName = "DNI")
    private Persona personaByDni;

    public Contacto() {
    }

    public Contacto(Integer telephone, String street, String email, Persona persona) {
        this.telephone = telephone;
        this.street = street;
        this.email = email;
        this.persona = persona;
        this.personaByDni = persona;
    }

    public Contacto(Integer telephone, String street, String email, Persona persona, Persona personaByDni) {
        this.telephone = telephone;
        this.street = street;
        this.email = email;
        this.persona = persona;
        this.personaByDni = personaByDni;
    }

    public Persona getPersona() {
        return persona;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public String getStreet() {
        return street;
    }

    public String getEmail() {
        return email;
    }

    public Persona getPersonaByDni() {
        return personaByDni;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPersonaByDni(Persona personaByDni) {
        this.personaByDni = personaByDni;
    }
    
}
