package com.alkemy.disney.service;

import com.alkemy.disney.dto.PersonajeBasicDTO;
import com.alkemy.disney.dto.PersonajeDTO;
import com.alkemy.disney.entities.PersonajeEntity;

import java.util.List;
import java.util.Set;

public interface PersonajeService {


    PersonajeDTO getDetailsById(Long id);
    List<PersonajeBasicDTO> getAll();
    PersonajeEntity getEntityById(Long id);
    List<PersonajeDTO> getByFilters(String name, Long age, Set<Long> movies, String order);
    PersonajeDTO save(PersonajeDTO personajeDTO);
    PersonajeDTO update(Long id, PersonajeDTO personajeDTO);

    void addPelicula(Long id, Long idPelicula);
    void removePelicula(Long id, Long idPelicula);
    void delete(Long id);
}
