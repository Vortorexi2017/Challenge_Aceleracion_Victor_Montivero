package com.alkemy.disney.service.impl;

import com.alkemy.disney.dto.PeliculaBasicDTO;
import com.alkemy.disney.dto.PeliculaDTO;
import com.alkemy.disney.dto.PeliculaFiltersDTO;
import com.alkemy.disney.entities.PeliculaEntity;
import com.alkemy.disney.entities.PersonajeEntity;
import com.alkemy.disney.exception.ParamNotFound;
import com.alkemy.disney.mapper.PeliculaMapper;
import com.alkemy.disney.repository.PeliculaRepository;
import com.alkemy.disney.repository.specification.PeliculaSpecification;
import com.alkemy.disney.service.PeliculaService;
import com.alkemy.disney.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class PeliculaServiceImpl implements PeliculaService {
    private  PeliculaMapper peliculaMapper;
    private  PeliculaRepository peliculaRepository;
    private  PersonajeService personajeService;
    private PeliculaSpecification peliculaSpecification;


    @Autowired
    public PeliculaServiceImpl(
            PeliculaRepository peliculaRepository,
            PeliculaMapper peliculaMapper,
            @Lazy PersonajeService personajeService,
            PeliculaSpecification peliculaSpecification
    ){
        this.peliculaRepository = peliculaRepository;
        this.peliculaMapper = peliculaMapper;
        this.personajeService = personajeService;
        this.peliculaSpecification = peliculaSpecification;
    }


    public PeliculaDTO getDetailsById(Long id) {

        Optional<PeliculaEntity> entity = this.peliculaRepository.findById(id);
        if(!entity.isPresent()){
            throw new ParamNotFound("Id pelicula no valido");
        }
        PeliculaDTO peliculaDTO = this.peliculaMapper.peliculaEntity2DTO(entity.get(), true);
        return peliculaDTO;
    }


    public PeliculaEntity getEntityById(Long id) {
        PeliculaEntity entity = this.peliculaRepository.getById(id);
        return entity;
    }


    public List<PeliculaBasicDTO> getAll() {
        List<PeliculaEntity> entities = this.peliculaRepository.findAll();
        List<PeliculaBasicDTO> peliculaBasicDTO = this.peliculaMapper.peliculaEntitySet2BasicDTOList(entities);
        return peliculaBasicDTO;
    }


    public List<PeliculaDTO> getByFilters(String name, Set<String> genre, String order) {

        PeliculaFiltersDTO filtersDTO = new PeliculaFiltersDTO(name, genre, order);
        List<PeliculaEntity> entities = this.peliculaRepository.findAll(this.peliculaSpecification.getByFilters(filtersDTO));
        List<PeliculaDTO> dtos = (List<PeliculaDTO>) this.peliculaMapper.peliculaEntitySet2DTOList(entities, true);
        return dtos;
    }


    public PeliculaDTO save(PeliculaDTO peliculaDTO) {

        PeliculaEntity entity = this.peliculaMapper.peliculaDTO2Entity(peliculaDTO);
        PeliculaEntity entitySaved = this.peliculaRepository.save(entity);
        PeliculaDTO result = this.peliculaMapper.peliculaEntity2DTO(entitySaved, false);
        return result;
    }


    public PeliculaDTO update(Long id, PeliculaDTO peliculaDTO) {

        Optional<PeliculaEntity> entity = this.peliculaRepository.findById(id);
        //PeliculaDTO peliculaDTO = this.peliculaMapper.peliculaEntity2DTO(entity.get(), false);
        if(!entity.isPresent()){
            throw new ParamNotFound("Id pelicula no valido");
        }

        this.peliculaMapper.peliculaEntityRefreshValues(entity.get(), peliculaDTO);
        PeliculaEntity entitySaved = this.peliculaRepository.save(entity.get());
        PeliculaDTO result = this.peliculaMapper.peliculaEntity2DTO(entitySaved, false);
        return result;
    }


    public void addPersonaje(Long id, Long idPersonaje) {

        PeliculaEntity entity = this.peliculaRepository.getById(id);
        entity.getPersonajes().size();
        PersonajeEntity personajeEntity = this.personajeService.getEntityById(idPersonaje);
        entity.addPersonaje(personajeEntity);
        this.peliculaRepository.save(entity);

    }


    public void removePersonaje(Long id, Long idPersonaje) {

        PeliculaEntity entity = this.peliculaRepository.getById(id);
        entity.getPersonajes().size();
        PersonajeEntity personajeEntity = this.personajeService.getEntityById(idPersonaje);
        entity.removePersonaje(personajeEntity);
        this.peliculaRepository.save(entity);


    }

    @Override
    public void delete(Long id) {
        this.peliculaRepository.deleteById(id);
    }
}
