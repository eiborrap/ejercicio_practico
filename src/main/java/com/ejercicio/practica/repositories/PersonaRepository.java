package com.ejercicio.practica.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ejercicio.practica.models.Persona;
import java.util.List;


public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    public Persona getById(Integer id);
    public Persona getByDni(String dni);

    public void deleteById(Integer id);
    public void deleteAll();
}
