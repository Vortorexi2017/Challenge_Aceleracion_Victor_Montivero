package com.alkemy.disney.service.impl;


import com.alkemy.disney.dto.PersonajeBasicDTO;
import com.alkemy.disney.dto.PersonajeDTO;
import com.alkemy.disney.dto.PersonajeFiltersDTO;
import com.alkemy.disney.entities.PeliculaEntity;
import com.alkemy.disney.entities.PersonajeEntity;
import com.alkemy.disney.exception.ParamNotFound;
import com.alkemy.disney.mapper.PersonajeMapper;
import com.alkemy.disney.repository.PersonajeRepository;
import com.alkemy.disney.repository.specification.PersonajeSpecification;
import com.alkemy.disney.service.PeliculaService;
import com.alkemy.disney.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonajeServiceImpl implements PersonajeService {
    private  PersonajeRepository personajeRepository;
    private  PersonajeMapper personajeMapper;
    private  PeliculaService peliculaService;
    private PersonajeSpecification personajeSpecification;

    @Autowired
    public PersonajeServiceImpl(
            PersonajeRepository personajeRepository,
            PersonajeMapper personajeMapper,
            PeliculaService peliculaService,
            PersonajeSpecification personajeSpecification){
        this.personajeRepository = personajeRepository;
        this.personajeMapper = personajeMapper;
        this.peliculaService = peliculaService;
        this.personajeSpecification = personajeSpecification;
    }


    public PersonajeDTO getDetailsById(Long id) {


        Optional<PersonajeEntity> entity = this.personajeRepository.findById(id);
        if(!entity.isPresent()){
            throw new ParamNotFound("Id icono no valido");
        }
        PersonajeDTO personajeDTO = this.personajeMapper.personajeEntity2DTO(entity.get(), true);
        return personajeDTO;

    }

    @Override
    public List<PersonajeBasicDTO> getAll() {
        List<PersonajeEntity> entities = this.personajeRepository.findAll();
        List<PersonajeBasicDTO> personajeBasicDTO = this.personajeMapper.personajeEntitySet2BasicDTOList(entities);
        return personajeBasicDTO;
    }


    public PersonajeEntity getEntityById(Long id) {
        PersonajeEntity entity = this.personajeRepository.getById(id);
        return entity;
    }

    @Override
    public List<PersonajeDTO> getByFilters(String name, Long age, Set<Long> movies, String order) {

        PersonajeFiltersDTO filtersDTO = new PersonajeFiltersDTO(name, age, movies, order);
        List<PersonajeEntity> entities = this.personajeRepository.findAll(this.personajeSpecification.getByFilters(filtersDTO));
        List<PersonajeDTO> dtos = (List<PersonajeDTO>) this.personajeMapper.personajeEntitySet2DTOList(entities, true);
        return dtos;
    }

    public PersonajeDTO save(PersonajeDTO personajeDTO) {

        PersonajeEntity entity = this.personajeMapper.personajeDTO2Entity(personajeDTO);
        PersonajeEntity entitySaved = this.personajeRepository.save(entity);
        PersonajeDTO result = this.personajeMapper.personajeEntity2DTO(entitySaved, false);
        return result;
    }

    public PersonajeDTO update(Long id, PersonajeDTO personajeDTO) {

        Optional<PersonajeEntity> entity = this.personajeRepository.findById(id);
        //PersonajeDTO personajeDTO = this.personajeMapper.personajeEntity2DTO(entity.get(), false);
        if(!entity.isPresent()){
            throw new ParamNotFound("Id personaje no valido");
        }
        this.personajeMapper.personajeEntityRefreshValues(entity.get(), personajeDTO);
        PersonajeEntity entitySaved = this.personajeRepository.save(entity.get());
        PersonajeDTO result = this.personajeMapper.personajeEntity2DTO(entitySaved, false);
        return result;
    }

    public void addPelicula(Long id, Long idPelicula) {

        PersonajeEntity entity = this.personajeRepository.getById(id);
        entity.getPeliculas().size();
        PeliculaEntity peliculaEntity = this.peliculaService.getEntityById(idPelicula);
        entity.addPelicula(peliculaEntity);
        this.personajeRepository.save(entity);

    }

    public void removePelicula(Long id, Long idPelicula) {

        PersonajeEntity entity = this.personajeRepository.getById(id);
        entity.getPeliculas().size();
        PeliculaEntity peliculaEntity = this.peliculaService.getEntityById(idPelicula);
        entity.removePelicula(peliculaEntity);
        this.personajeRepository.save(entity);

    }

    public void delete(Long id) {
        this.personajeRepository.deleteById(id);
    }
}
