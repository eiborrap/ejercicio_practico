package com.ejercicio.practica.controllers;

import com.ejercicio.practica.dtos.ContactDetailsDTO;
import com.ejercicio.practica.dtos.PersonaDTO;
import com.ejercicio.practica.services.PersonaServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PersonasApiControllerTest {

    private MockMvc mockMvc;

    private PersonaServices personaServices;

    @BeforeEach
    void setUp() {
        personaServices = org.mockito.Mockito.mock(PersonaServices.class);
        PersonasApiController controller = new PersonasApiController();
        controller.personaServices = personaServices;

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllPersonas_returnsList() throws Exception {
        PersonaDTO p1 = validPersonaDto();
        PersonaDTO p2 = validPersonaDto();

        when(personaServices.getAllPersonas()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/personas"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(personaServices).getAllPersonas();
    }

    @Test
    void createPersona_validBody_returnsOkAndPersona() throws Exception {
        PersonaDTO response = validPersonaDto();

        when(personaServices.createPersona(any(PersonaDTO.class))).thenReturn(response);

        mockMvc.perform(post("/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Pepe",
                                  "lastName": "Perez",
                                  "fullName": "Pepe Perez",
                                  "dni": "12345678Z",
                                  "contactDetails": {
                                    "telephone": 666666666,
                                    "email": "pepe@example.com"
                                  }
                                }
                                """))
                .andExpect(status().isOk());

        verify(personaServices).createPersona(any(PersonaDTO.class));
    }

    @Test
    void createPersona_whenServiceThrowsIllegalArgument_returnsBadRequest() throws Exception {
        // PersonaServices maps duplicate DNI (DataIntegrityViolationException) to IllegalArgumentException
        doThrow(new IllegalArgumentException("Dni already exists."))
                .when(personaServices).createPersona(any(PersonaDTO.class));

        mockMvc.perform(post("/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Pepe",
                                  "lastName": "Perez",
                                  "fullName": "Pepe Perez",
                                  "dni": "12345678Z",
                                  "contactDetails": {
                                    "telephone": 666666666,
                                    "email": "pepe@example.com"
                                  }
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPersona_invalidDniPath_returnsBadRequest() throws Exception {
        // Invalid DNI format should be rejected by @Dni validator on @PathVariable
        mockMvc.perform(get("/personas/{DNI}", "INVALID"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPersona_validDni_callsServiceAndReturnsOk() throws Exception {
        PersonaDTO response = validPersonaDto();
        when(personaServices.getByDni(eq("12345678Z"))).thenReturn(response);

        mockMvc.perform(get("/personas/{DNI}", "12345678Z"))
                .andExpect(status().isOk());

        verify(personaServices).getByDni("12345678Z");
    }

    @Test
    void updatePersona_whenServiceThrowsIllegalArgument_returnsBadRequest() throws Exception {

        doThrow(new IllegalArgumentException("bad data"))
                .when(personaServices).updatePersona(eq("12345678Z"), any(PersonaDTO.class));

        mockMvc.perform(put("/personas/{DNI}", "12345678Z")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Pepe",
                                  "lastName": "Perez",
                                  "fullName": "Pepe Perez",
                                  "dni": "12345678Z",
                                  "contactDetails": {
                                    "telephone": 666666666,
                                    "email": "pepe@example.com"
                                  }
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatePersona_valid_callsServiceAndReturnsOk() throws Exception {
        
        when(personaServices.updatePersona(eq("12345678Z"), any(PersonaDTO.class))).thenReturn(null);

        mockMvc.perform(put("/personas/{DNI}", "12345678Z")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Pepe",
                                  "lastName": "Perez",
                                  "fullName": "Pepe Perez",
                                  "dni": "12345678Z",
                                  "contactDetails": {
                                    "telephone": 666666666,
                                    "email": "pepe@example.com"
                                  }
                                }
                                """))
                .andExpect(status().isOk());

        verify(personaServices).updatePersona(eq("12345678Z"), any(PersonaDTO.class));
    }

    @Test
    void deletePersona_valid_callsServiceAndReturnsNoContent() throws Exception {
        doNothing().when(personaServices).deleteByDni("12345678Z");

        mockMvc.perform(delete("/personas/{DNI}", "12345678Z"))
                .andExpect(status().isNoContent());

        verify(personaServices).deleteByDni("12345678Z");
    }

    @Test
    void deleteAllPersona_callsServiceAndReturnsNoContent() throws Exception {
        doNothing().when(personaServices).deleteAllPersona();

        mockMvc.perform(delete("/personas"))
                .andExpect(status().isNoContent());

        verify(personaServices).deleteAllPersona();
    }

    private static PersonaDTO validPersonaDto() {
        return new PersonaDTO.Builder()
                .setName("Pepe")
                .setLastName("Perez")
                .setFullName("Pepe Perez")
                .setDni("12345678Z")
                .setContactDetails(new ContactDetailsDTO.Builder()
                        .setTelephone(666666666)
                        .setEmail("pepe@example.com")
                        .build())
                .build();
    }
}
