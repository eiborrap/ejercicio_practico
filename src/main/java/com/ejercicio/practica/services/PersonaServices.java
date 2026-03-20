package com.ejercicio.practica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejercicio.practica.dtos.ContactDetailsDTO;
import com.ejercicio.practica.dtos.PersonaDTO;
import com.ejercicio.practica.mappers.Mapper;
import com.ejercicio.practica.models.Contacto;
import com.ejercicio.practica.models.Persona;
import com.ejercicio.practica.repositories.ContactoRepository;
import com.ejercicio.practica.repositories.PersonaRepository;

@Service
public class PersonaServices {
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private ContactoRepository contactoRepository;

    public List<PersonaDTO> getAllPersonas(){
        List<Persona> personas = personaRepository.findAll();
        personas.forEach(Mapper::toPersonaDTO);
        return personas.stream().map(Mapper::toPersonaDTO).toList();
    }
    public PersonaDTO getByDNI(String dni){
        Persona p = personaRepository.getByDni(dni);
        return Mapper.toPersonaDTO(p);
    }

    public PersonaDTO createPersona(PersonaDTO newPersona){
        Persona persona = Mapper.toNewPersona(newPersona);
        personaRepository.save(persona);

        ContactDetailsDTO contactDetailsDTO = newPersona.getContactDetails();
        Contacto contacto = new Contacto(
            contactDetailsDTO.getTelephone(),
            contactDetailsDTO.getStreet(),
            contactDetailsDTO.getEmail(),
            persona);
        contactoRepository.save(contacto);

        return Mapper.toPersonaDTO(personaRepository.getById(persona.getId()));
    }

    public void deleteByDni(String dni){
        Persona p = personaRepository.getByDni(dni);
        personaRepository.deleteById(p.getId());
    }

    public void deleteAllPersona(){
        personaRepository.deleteAll();
    }
}
