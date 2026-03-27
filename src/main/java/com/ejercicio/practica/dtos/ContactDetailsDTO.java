package com.ejercicio.practica.dtos;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.validation.constraints.Email;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = ContactDetailsDTO.Builder.class)
public class ContactDetailsDTO implements Serializable{
    private final Integer telephone;
    private final String street; //Optional, can return null
    @Email
    private final String email; //Optional, can return null

    private ContactDetailsDTO(Builder builder){
        this.telephone = builder.telephone;
        this.street = builder.street;
        this.email = builder.email;
    }



    public Integer getTelephone(){
        return telephone; //It can't be null, telephone is mandatory
    }
    public String getStreet(){
        return street;
    }
    public String getEmail(){
        return email;
    }

        
    protected ContactDetailsDTO copy(){
        return new ContactDetailsDTO.Builder().setTelephone(telephone).setStreet(street).setEmail(email).build();
    }

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
    public static final class Builder{
        private Integer telephone; //obligatorio
        private String street;
        private String email;

        public Builder() {};

        public Builder setTelephone(Integer telephone){
            this.telephone = telephone;
            return this;
        }
        
        public Builder setStreet(String street){
            this.street = street;
            return this;
        }

        public Builder setEmail(String email){
            this.email = email;
            return this;
        }

        public ContactDetailsDTO build(){
            if(telephone == null){
                throw new IllegalArgumentException("telephone is mandatory");
            }
            return new ContactDetailsDTO(this);
        }
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("telephone",telephone)
            .append("street",street)
            .append("email",email)
            .toString();
    }
}
