package com.ejercicio.practica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ejercicio.practica.dtos.PersonaDTO;
import com.ejercicio.practica.mappers.Mapper;
import com.ejercicio.practica.models.Persona;
import com.ejercicio.practica.repositories.PersonaRepository;

/**
 * Service layer for managing {@link Persona} entities.
 *
 * <p>Provides CRUD-style operations and maps between persistence entities and API DTOs
 * using {@link Mapper}.</p>
 */
@Service
public class PersonaServices {
    @Autowired
    private PersonaRepository personaRepository;

    /**
     * Retrieves all persons from the database and maps them to {@link PersonaDTO}.
     *
     * @return list of persons as DTOs
     */
    public List<PersonaDTO> getAllPersonas(){
        List<Persona> personas = personaRepository.findAll();
        personas.forEach(Mapper::toPersonaDTO);
        return personas.stream().map(Mapper::toPersonaDTO).toList();
    }
    /**
     * Retrieves a person by their DNI and maps it to {@link PersonaDTO}.
     *
     * @param dni unique DNI
     * @return person as DTO
     */
    public PersonaDTO getByDni(String dni){
        Persona p = personaRepository.getByDni(dni);
        return Mapper.toPersonaDTO(p);
    }

    /**
     * Creates a new person from the given DTO.
     *
     * <p>This method is {@link Transactional} to ensure the persist operation is executed
     * within a transaction.</p>
     *
     * @param newPersona DTO containing the data to persist
     * @return persisted person as DTO (including generated id)
     */
    @Transactional
    public PersonaDTO createPersona(PersonaDTO newPersona){
        Persona persona = Mapper.toPersona(newPersona);
        personaRepository.save(persona);
        
        return Mapper.toPersonaDTO(personaRepository.getById(persona.getId()));
    }

    /**
     * Updates an existing person identified by DNI with the data from the given DTO.
     *
     * <p>Rules:</p>
     * <ul>
     *   <li>The {@code dni} path parameter must match {@code personaDto.getDni()}.</li>
     *   <li>If the DNI does not exist, an {@link IllegalArgumentException} is thrown.</li>
     * </ul>
     *
     * @param dni DNI of the person to update
     * @param personaDto DTO with the new data (must contain the same DNI)
     * @return the provided DTO once the update is applied
     * @throws IllegalArgumentException if DNI does not match or does not exist
     */
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

    /**
     * Deletes a person by DNI.
     *
     * @param dni DNI of the person to delete
     */
    public void deleteByDni(String dni){
        Persona p = personaRepository.getByDni(dni);
        personaRepository.deleteById(p.getId());
    }

    /**
     * Deletes all persons.
     */
    public void deleteAllPersona(){
        personaRepository.deleteAll();
    }
}
