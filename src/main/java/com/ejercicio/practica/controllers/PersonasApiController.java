package com.ejercicio.practica.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejercicio.practica.dtos.PersonaDTO;

import java.util.ArrayList;

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
    @GetMapping
    public ArrayList<String> getAllPersonas() {
        return new ArrayList<String>();
    }

    @PostMapping
    public ResponseEntity<PersonaDTO> createPersona(@RequestBody PersonaDTO persona) {
        return ResponseEntity.ok(new PersonaDTO(null));
    }

    @GetMapping("/{DNI}")
    public ResponseEntity<PersonaDTO> getPersona(@PathVariable String DNI) {
        return ResponseEntity.ok(new PersonaDTO(null));
    }

    @PutMapping("/{DNI}")
    public ResponseEntity<PersonaDTO> updatePersona(@PathVariable String DNI, @RequestBody PersonaDTO persona) {
        return ResponseEntity.ok(new PersonaDTO(null));
    }

    @DeleteMapping("/{DNI}")
    public boolean deletePersona(@PathVariable String DNI) {
        return false;
    }

    @DeleteMapping
    public boolean deleteAllPersona() {
        return false;
    }
    
    

}
