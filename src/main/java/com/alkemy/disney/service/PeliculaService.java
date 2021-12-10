package com.alkemy.disney.service;

import com.alkemy.disney.dto.PeliculaBasicDTO;
import com.alkemy.disney.dto.PeliculaDTO;
import com.alkemy.disney.entities.PeliculaEntity;

import java.util.List;
import java.util.Set;

public interface PeliculaService {

    PeliculaDTO getDetailsById(Long id);
    PeliculaEntity getEntityById(Long id);
    List<PeliculaBasicDTO> getAll();
    List<PeliculaDTO> getByFilters(String name,Set<String> genre, String order);
    PeliculaDTO save(PeliculaDTO peliculaDTO);
    PeliculaDTO update(Long id, PeliculaDTO peliculaDTO);

    void addPersonaje(Long id, Long idPersonaje);
    void removePersonaje(Long id, Long idPersonaje);
    void delete(Long id);
}
