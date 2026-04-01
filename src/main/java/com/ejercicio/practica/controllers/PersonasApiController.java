package com.ejercicio.practica.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejercicio.practica.annotations.Dni;
import com.ejercicio.practica.dtos.PersonaDTO;
import com.ejercicio.practica.services.PersonaServices;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * REST controller exposing CRUD endpoints for {@code /personas}.
 *
 * <p>Delegates business logic to {@link PersonaServices}.</p>
 */
@RestController
@RequestMapping("/personas")
public class PersonasApiController {
    @Autowired
    PersonaServices personaServices;

    @GetMapping
    public List<PersonaDTO> getAllPersonas() {
        return personaServices.getAllPersonas();
    }

    @PostMapping
    public ResponseEntity<Object> createPersona(@Valid @RequestBody PersonaDTO persona) {
        try{
            return ResponseEntity.ok(personaServices.createPersona(persona));
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/{DNI}")
    public ResponseEntity<PersonaDTO> getPersona(@PathVariable @Dni String DNI) {
        return ResponseEntity.ok(personaServices.getByDni(DNI));
    }

    @PutMapping("/{DNI}")
    public ResponseEntity<Object> updatePersona(@PathVariable @Dni String DNI, @RequestBody PersonaDTO persona) {
        try{
            personaServices.updatePersona(DNI, persona);
            return ResponseEntity.ok().build();

        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/{DNI}")
    public ResponseEntity<Object> deletePersona(@PathVariable @Dni String DNI) {
        personaServices.deleteByDni(DNI);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAllPersona() {
        personaServices.deleteAllPersona();
        return ResponseEntity.noContent().build();
    }
    
    

}
