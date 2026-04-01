package com.ejercicio.practica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.ejercicio.practica.services.PersonaServices;

@Controller
public class PersonaController {
    @Autowired
    private PersonaServices personaServices;

    @GetMapping("/")
    public String viewAllData(Model model) {
        model.addAttribute("list",personaServices.getAllPersonas());
        return "index";
    }
    
}
