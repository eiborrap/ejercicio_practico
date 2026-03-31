package com.ejercicio.practica.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ejercicio.practica.dtos.ContactDetailsDTO;
import com.ejercicio.practica.dtos.PersonaDTO;
import com.ejercicio.practica.models.Contacto;
import com.ejercicio.practica.models.Persona;
import com.ejercicio.practica.repositories.PersonaRepository;

@ExtendWith(MockitoExtension.class)
class PersonaServicesTest {

    @Mock
    private PersonaRepository personaRepository;

    @InjectMocks
    private PersonaServices personaServices;

    private static Persona persona(int id, String name, String lastName, String dni) {
        Persona p = new Persona(id, name, lastName, dni);
        p.setContacto(new Contacto(600000000, "Calle Falsa 123", "a@b.com", p));
        return p;
    }

    private static PersonaDTO personaDto(int id, String name, String lastName, String dni) {
        return new PersonaDTO.Builder()
                .setIdPerson(id)
                .setName(name)
                .setLastName(lastName)
                .setDni(dni)
                .setFullName(name + " " + lastName)
                .setContactDetails(new ContactDetailsDTO.Builder()
                        .setTelephone(600000000)
                        .setStreet("Calle Falsa 123")
                        .setEmail("a@b.com")
                        .build())
                .build();
    }

    @Test
    void getAllPersonas_returnsMappedDtos() {
        Persona p1 = persona(1, "Ada", "Lovelace", "11111111A");
        Persona p2 = persona(2, "Alan", "Turing", "22222222B");

        when(personaRepository.findAll()).thenReturn(List.of(p1, p2));

        List<PersonaDTO> result = personaServices.getAllPersonas();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("Ada", result.get(0).getName());
        assertEquals("Lovelace", result.get(0).getLastName());
        assertEquals("11111111A", result.get(0).getDni());
        assertNotNull(result.get(0).getContactDetails());
        assertEquals(600000000, result.get(0).getContactDetails().getTelephone());

        assertEquals("Alan", result.get(1).getName());
        assertEquals("Turing", result.get(1).getLastName());
        assertEquals("22222222B", result.get(1).getDni());

        verify(personaRepository).findAll();
        verifyNoMoreInteractions(personaRepository);
    }

    @Test
    void getByDni_mapsEntityToDto() {
        Persona p = persona(3, "Grace", "Hopper", "33333333C");
        when(personaRepository.getByDni("33333333C")).thenReturn(p);

        PersonaDTO result = personaServices.getByDni("33333333C");

        assertNotNull(result);
        assertEquals("Grace", result.getName());
        assertEquals("Hopper", result.getLastName());
        assertEquals("33333333C", result.getDni());
        assertEquals(3, result.getIdPerson());
        assertEquals("Grace Hopper", result.getFullName());

        verify(personaRepository).getByDni("33333333C");
        verifyNoMoreInteractions(personaRepository);
    }

    @Test
    void createPersona_savesEntity_andReturnsDtoFetchedById() {
        PersonaDTO input = personaDto(0, "Linus", "Torvalds", "44444444D");

        // Service maps DTO -> Persona and then saves; we capture what is saved.
        doAnswer((InvocationOnMock invocation) -> {
            Persona saved = (Persona) invocation.getArguments()[0];
            // emulate JPA id generation
            saved.setId(10);
            return saved;
        }).when(personaRepository).save(any(Persona.class));

        Persona fromDb = persona(10, "Linus", "Torvalds", "44444444D");
        when(personaRepository.getById(10)).thenReturn(fromDb);

        PersonaDTO result = personaServices.createPersona(input);

        ArgumentCaptor<Persona> captor = ArgumentCaptor.forClass(Persona.class);
        verify(personaRepository).save(captor.capture());
        Persona savedEntity = captor.getValue();
        assertEquals("Linus", savedEntity.getName());
        assertEquals("Torvalds", savedEntity.getLastname());
        assertEquals("44444444D", savedEntity.getDni());
        assertNotNull(savedEntity.getContacto());
        assertEquals(600000000, savedEntity.getContacto().getTelephone());

        verify(personaRepository).getById(10);
        verifyNoMoreInteractions(personaRepository);

        assertNotNull(result);
        assertEquals(10, result.getIdPerson());
        assertEquals("Linus", result.getName());
        assertEquals("Torvalds", result.getLastName());
        assertEquals("Linus Torvalds", result.getFullName());
    }

    @Test
    void updatePersona_throwsWhenPathDniDoesNotMatchDtoDni() {
        PersonaDTO dto = personaDto(1, "Ada", "Lovelace", "99999999Z");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> personaServices.updatePersona("11111111A", dto));

        assertEquals("Dni must be equals to the object's dni that you want to modify", ex.getMessage());
        verifyNoMoreInteractions(personaRepository);
    }

    @Test
    void updatePersona_throwsWhenDniDoesNotExist() {
        PersonaDTO dto = personaDto(1, "Ada", "Lovelace", "11111111A");
        when(personaRepository.getByDni("11111111A")).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> personaServices.updatePersona("11111111A", dto));

        assertEquals("Dni doesn't exist.", ex.getMessage());
        verify(personaRepository).getByDni("11111111A");
        verifyNoMoreInteractions(personaRepository);
    }

    @Test
    void updatePersona_updatesFieldsAndSavesExistingPersona() {
        Persona existing = persona(5, "Old", "Name", "55555555E");
        existing.getContacto().setTelephone(111);
        existing.getContacto().setEmail("old@mail.com");
        existing.getContacto().setStreet("Old Street");

        when(personaRepository.getByDni("55555555E")).thenReturn(existing);

        PersonaDTO newData = new PersonaDTO.Builder()
                .setIdPerson(5)
                .setName("New")
                .setLastName("Data")
                .setDni("55555555E")
                .setFullName("New Data")
                .setContactDetails(new ContactDetailsDTO.Builder()
                        .setTelephone(222)
                        .setEmail("new@mail.com")
                        .setStreet("New Street")
                        .build())
                .build();

        PersonaDTO result = personaServices.updatePersona("55555555E", newData);

        assertSame(newData, result);

        ArgumentCaptor<Persona> savedCaptor = ArgumentCaptor.forClass(Persona.class);
        verify(personaRepository).getByDni("55555555E");
        verify(personaRepository).save(savedCaptor.capture());
        verifyNoMoreInteractions(personaRepository);

        Persona saved = savedCaptor.getValue();
        assertEquals(5, saved.getId());
        assertEquals("New", saved.getName());
        assertEquals("Data", saved.getLastname());
        assertEquals("55555555E", saved.getDni());
        assertNotNull(saved.getContacto());
        assertEquals(222, saved.getContacto().getTelephone());
        assertEquals("new@mail.com", saved.getContacto().getEmail());
        assertEquals("New Street", saved.getContacto().getStreet());
    }

    @Test
    void deleteByDni_deletesByIdFromEntity() {
        Persona existing = persona(7, "To", "Delete", "77777777G");
        when(personaRepository.getByDni("77777777G")).thenReturn(existing);

        personaServices.deleteByDni("77777777G");

        verify(personaRepository).getByDni("77777777G");
        verify(personaRepository).deleteById(7);
        verifyNoMoreInteractions(personaRepository);
    }

    @Test
    void deleteAllPersona_deletesAll() {
        personaServices.deleteAllPersona();

        verify(personaRepository).deleteAll();
        verifyNoMoreInteractions(personaRepository);
    }
}
