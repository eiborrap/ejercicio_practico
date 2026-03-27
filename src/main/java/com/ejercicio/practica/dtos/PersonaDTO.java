package com.ejercicio.practica.dtos;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ejercicio.practica.annotations.Dni;

import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema
@JsonDeserialize(builder = PersonaDTO.Builder.class)
public class PersonaDTO implements Serializable{
    private final Integer idPerson;
    @Size(max=30)
    private final String name;
    private final String fullName;
    @Size(max=50)
    private final String lastName;
    @Dni
    private final String dni;
    private final ContactDetailsDTO contactDetails;

    public PersonaDTO(PersonaDTO.Builder builder){
        idPerson = builder.idPerson;
        name = builder.name;
        lastName = builder.lastName;
        fullName = builder.fullName;
        dni = builder.dni;
        contactDetails = builder.contactDetails;
    }

    public Integer getIdPerson() {
        return idPerson;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }
    
    public String getDni() {
        return dni;
    }

    public ContactDetailsDTO getContactDetails() {
        return contactDetails;
    }

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
    public static final class Builder{
        private Integer idPerson;
        private String name;
        private String lastName;
        private String fullName;
        private String dni;
        private ContactDetailsDTO contactDetails;
        
        public Builder(){};
        
        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Builder setLastName(String lastName){
            this.lastName = lastName;
            return this;
        }
        
        public Builder setDni(String dni){
            this.dni = dni;
            return this;
        }

        public Builder setIdPerson(Integer idPerson){
            this.idPerson = idPerson;
            return this; 
        }
        public Builder setFullName(String fullName){
            this.fullName = fullName;
            return this;
        }

        public Builder setContactDetails(ContactDetailsDTO contactDetailsDTO){
            this.contactDetails = contactDetailsDTO;
            return this;
        }

        public PersonaDTO build(){
            if(name == null || StringUtils.isBlank(name)){
                throw new IllegalArgumentException("name is mandatory");
            }
            if(lastName == null || StringUtils.isBlank(lastName)){
                throw new IllegalArgumentException("lastName is mandatory");
            }
            if(dni == null || StringUtils.isBlank(dni)){
                System.out.println("DNI:"+ dni);
                throw new IllegalArgumentException("dni is mandatory");
            }
            if(contactDetails == null){
                throw new IllegalArgumentException("contactDetails is mandatory");
            }
            return new PersonaDTO(this);
        }
    }


    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("idPerson",idPerson)
            .append("name",name)
            .append("lastName",lastName)
            .append("fullName",fullName)
            .append("DNI",dni)
            .append("contactDetails",contactDetails)
            .toString();
    }
}
