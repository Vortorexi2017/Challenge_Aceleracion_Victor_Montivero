package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.PeliculaBasicDTO;
import com.alkemy.disney.dto.PeliculaDTO;
import com.alkemy.disney.dto.PersonajeDTO;
import com.alkemy.disney.entities.PeliculaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class PeliculaMapper {


    private PersonajeMapper personajeMapper;

    @Autowired
    public PeliculaMapper(@Lazy PersonajeMapper personajeMapper){
        this.personajeMapper = personajeMapper;
    }

    public PeliculaEntity peliculaDTO2Entity (PeliculaDTO dto){
        PeliculaEntity entity = new PeliculaEntity();
        entity.setImagen(dto.getImagen());
        entity.setTitulo(dto.getTitulo());
        entity.setFechaCreacion(
                string2LocalDate(dto.getFechaCreacion())
        );
        entity.setCalificacion(dto.getCalificacion());
        //TODO: Ver problema con listas
        //entity.setPersonajes(dto.getPersonajes());
        return entity;

    }

    private LocalDate string2LocalDate (String stringDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(stringDate, formatter);
        return date;
    }

    public PeliculaDTO peliculaEntity2DTO (PeliculaEntity entity, boolean loadPersonajes){
        PeliculaDTO dto = new PeliculaDTO();
        dto.setId(entity.getId());
        dto.setImagen(entity.getImagen());
        dto.setTitulo(entity.getTitulo());
        dto.setFechaCreacion(entity.getFechaCreacion().toString());
        dto.setCalificacion(entity.getCalificacion());
        if(loadPersonajes){
            Set<PersonajeDTO> personajesDTO = this.personajeMapper.personajeEntitySet2DTOList(entity.getPersonajes(), false);
            dto.setPersonajes(personajesDTO);
        }
        return dto;
    }

    public void peliculaEntityRefreshValues (PeliculaEntity entity, PeliculaDTO peliculaDTO){
        entity.setImagen(peliculaDTO.getImagen());
        entity.setTitulo(peliculaDTO.getTitulo());
        entity.setFechaCreacion(
                string2LocalDate(peliculaDTO.getFechaCreacion())
        );
        entity.setCalificacion(peliculaDTO.getCalificacion());
    }

    public Set<PeliculaEntity> peliculaDTOList2Entity (List<PeliculaDTO> dtos){
        Set<PeliculaEntity> entities = new HashSet<>();
        for(PeliculaDTO dto: dtos){
            entities.add(peliculaDTO2Entity(dto));
        }
        return entities;
    }

    public Set<PeliculaDTO> peliculaEntitySet2DTOList (Collection<PeliculaEntity> entities, boolean loadPersonajes){
        Set<PeliculaDTO> dtos = new HashSet<>();
        for(PeliculaEntity entity: entities){
            dtos.add(peliculaEntity2DTO(entity, loadPersonajes));
        }
        return dtos;
    }

    public List<PeliculaBasicDTO> peliculaEntitySet2BasicDTOList (Collection<PeliculaEntity> entities){
        List<PeliculaBasicDTO> dtos = new ArrayList<>();
        PeliculaBasicDTO basicDTO;
        for(PeliculaEntity entity: entities){
            basicDTO = new PeliculaBasicDTO();
            basicDTO.setId(entity.getId());
            basicDTO.setImagen(entity.getImagen());
            basicDTO.setTitulo(entity.getTitulo());
            basicDTO.setFechaCreacion(entity.getFechaCreacion().toString());
            dtos.add(basicDTO);
        }
        return dtos;
    }
}
