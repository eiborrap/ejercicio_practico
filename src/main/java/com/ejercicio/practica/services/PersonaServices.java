package com.ejercicio.practica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejercicio.practica.dtos.PersonaDTO;
import com.ejercicio.practica.mappers.Mapper;
import com.ejercicio.practica.models.Persona;
import com.ejercicio.practica.repositories.ContactoRepository;
import com.ejercicio.practica.repositories.PersonaRepository;

@Service
public class PersonaServices {
    @Autowired
    private PersonaRepository personaRepository;
    //@Autowired
    //private ContactoRepository contactoRepository;

    public List<PersonaDTO> getAllPersonas(){
        List<Persona> personas = personaRepository.findAll();
        personas.forEach(Mapper::toPersonaDTO);
        return personas.stream().map(Mapper::toPersonaDTO).toList();
    }
    public PersonaDTO getByDNI(String dni){
        Persona p = personaRepository.getByDni(dni);
        return Mapper.toPersonaDTO(p);
    }


    public void deleteByDni(String dni){
        Persona p = personaRepository.getByDni(dni);
        personaRepository.deleteById(p.getId());
    }

    public void deleteAllPersona(){
        personaRepository.deleteAll();
    }
}
