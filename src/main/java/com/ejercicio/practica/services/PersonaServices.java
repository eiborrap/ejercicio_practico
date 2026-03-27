package com.ejercicio.practica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ejercicio.practica.dtos.PersonaDTO;
import com.ejercicio.practica.mappers.Mapper;
import com.ejercicio.practica.models.Persona;
import com.ejercicio.practica.repositories.PersonaRepository;

@Service
public class PersonaServices {
    @Autowired
    private PersonaRepository personaRepository;

    public List<PersonaDTO> getAllPersonas(){
        List<Persona> personas = personaRepository.findAll();
        personas.forEach(Mapper::toPersonaDTO);
        return personas.stream().map(Mapper::toPersonaDTO).toList();
    }
    public PersonaDTO getByDni(String dni){
        Persona p = personaRepository.getByDni(dni);
        return Mapper.toPersonaDTO(p);
    }

    @Transactional
    public PersonaDTO createPersona(PersonaDTO newPersona){
        Persona persona = Mapper.toPersona(newPersona);
        personaRepository.save(persona);
        
        return Mapper.toPersonaDTO(personaRepository.getById(persona.getId()));
    }

    public PersonaDTO updatePersona(String dni, PersonaDTO personaDto) throws IllegalArgumentException{
        if(!dni.equals(personaDto.getDni())){
            throw new IllegalArgumentException("Dni must be equals to the object's dni that you want to modify");
        }
        Persona existsPersona = personaRepository.getByDni(dni);
        if(existsPersona == null){
            throw new IllegalArgumentException("Dni doesn't exist.");
        }
        
        Persona newDataPersona = Mapper.toPersona(personaDto);

        existsPersona.setName(newDataPersona.getName());
        existsPersona.setLastname(newDataPersona.getLastname());
        existsPersona.getContacto().setTelephone(newDataPersona.getContacto().getTelephone());
        existsPersona.getContacto().setEmail(newDataPersona.getContacto().getEmail());
        existsPersona.getContacto().setStreet(newDataPersona.getContacto().getStreet());
        personaRepository.save(existsPersona);
        return personaDto;
    }

    public void deleteByDni(String dni){
        Persona p = personaRepository.getByDni(dni);
        personaRepository.deleteById(p.getId());
    }

    public void deleteAllPersona(){
        personaRepository.deleteAll();
    }
}
