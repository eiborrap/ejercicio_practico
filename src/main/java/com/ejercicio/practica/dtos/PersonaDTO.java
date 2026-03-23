package com.ejercicio.practica.dtos;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@JsonInclude
@JsonDeserialize(builder = PersonaDTO.Builder.class)
public class PersonaDTO implements Serializable{
    private final Integer idPerson;
    private final String name;
    private final String fullName;
    private final String lastName;
    private final String DNI;
    private final ContactDetailsDTO contactDetails;

    public PersonaDTO(PersonaDTO.Builder builder){
        idPerson = builder.idPerson;
        name = builder.name;
        lastName = builder.lastName;
        fullName = builder.fullName;
        DNI = builder.DNI;
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
    
    public String getDNI() {
        return DNI;
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
        private String DNI;
        private ContactDetailsDTO contactDetails;

        //Se piden todos los parámetros obligatorios (En este caso todos) 
        // para no crear un objeto incompleto
        @JsonCreator
        public Builder(
            @JsonProperty("name") String name, 
            @JsonProperty("lastName") String lastName, 
            @JsonProperty("DNI") String DNI, 
            @JsonProperty("contactDetails") ContactDetailsDTO contactDetails
        ){
            if(name == null || StringUtils.isBlank(name)){
                throw new IllegalArgumentException("name is mandatory");
            }
            if(lastName == null || StringUtils.isBlank(lastName)){
                throw new IllegalArgumentException("lastName is mandatory");
            }
            if(DNI == null || StringUtils.isBlank(DNI)){
                throw new IllegalArgumentException("DNI is mandatory");
            }
            if(contactDetails == null){
                throw new IllegalArgumentException("contactDetails is mandatory");
            }
            this.name = name;
            this.lastName = lastName;
            this.DNI = DNI;
            this.contactDetails = contactDetails;
        }

        public Builder setIdPerson(Integer idPerson){
            this.idPerson = idPerson;
            return this; 
        }
        public Builder setFullName(String fullName){
            this.fullName = fullName;
            return this;
        }

        public PersonaDTO build(){
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
            .append("DNI",DNI)
            .append("contactDetails",contactDetails)
            .toString();
    }
}
