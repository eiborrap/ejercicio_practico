package com.ejercicio.practica.controllers;

import com.ejercicio.practica.dtos.ContactDetailsDTO;
import com.ejercicio.practica.dtos.PersonaDTO;
import com.ejercicio.practica.services.PersonaServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class PersonaControllerTest {

    private MockMvc mockMvc;

    private PersonaServices personaServices;

    @BeforeEach
    void setUp() {
        personaServices = org.mockito.Mockito.mock(PersonaServices.class);

        PersonaController controller = new PersonaController();
        ReflectionTestUtils.setField(controller, "personaServices", personaServices);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void viewAllData_returnsIndexAndAddsListToModel() throws Exception {
        PersonaDTO p1 = validPersonaDto("12345678Z");
        PersonaDTO p2 = validPersonaDto("87654321X");

        when(personaServices.getAllPersonas()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("list"))
                .andExpect(model().attribute("list", hasSize(2)));

        verify(personaServices).getAllPersonas();
    }

    private static PersonaDTO validPersonaDto(String dni) {
        return new PersonaDTO.Builder()
                .setName("Pepe")
                .setLastName("Perez")
                .setFullName("Pepe Perez")
                .setDni(dni)
                .setContactDetails(new ContactDetailsDTO.Builder()
                        .setTelephone(666666666)
                        .setEmail("pepe@example.com")
                        .build())
                .build();
    }
}
