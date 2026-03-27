package com.ejercicio.practica.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejercicio.practica.dtos.ContactDetailsDTO;
import com.ejercicio.practica.dtos.PersonaDTO;
import com.ejercicio.practica.services.PersonaServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/personas")
public class PersonasApiController {
    @Autowired
    PersonaServices personaServices;

    @GetMapping
    public List<PersonaDTO> getAllPersonas() {
        return personaServices.getAllPersonas();
    }
    @PostMapping("/h")
    public ResponseEntity<Object> c(@RequestBody ContactDetailsDTO persona) {
        System.out.println(persona);
        return ResponseEntity.ok(null);
    }
    @PostMapping
    public ResponseEntity<PersonaDTO> createPersona(@RequestBody PersonaDTO persona) {
        return ResponseEntity.ok(personaServices.createPersona(persona));
    }

    @GetMapping("/{DNI}")
    public ResponseEntity<PersonaDTO> getPersona(@PathVariable String DNI) {
        return ResponseEntity.ok(personaServices.getByDni(DNI));
    }

    @PutMapping("/{DNI}")
    public ResponseEntity<Object> updatePersona(@PathVariable String DNI, @RequestBody PersonaDTO persona) {
        try{
            personaServices.updatePersona(DNI, persona);
            return ResponseEntity.ok().build();

        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/{DNI}")
    public ResponseEntity<Object> deletePersona(@PathVariable String DNI) {
        personaServices.deleteByDni(DNI);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAllPersona() {
        personaServices.deleteAllPersona();
        return ResponseEntity.noContent().build();
    }
    
    

}
