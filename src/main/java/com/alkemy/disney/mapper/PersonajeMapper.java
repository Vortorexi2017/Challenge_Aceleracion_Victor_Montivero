package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.PeliculaDTO;
import com.alkemy.disney.dto.PersonajeBasicDTO;
import com.alkemy.disney.dto.PersonajeDTO;
import com.alkemy.disney.entities.PersonajeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PersonajeMapper {


    private PeliculaMapper peliculaMapper;

    @Autowired
    public PersonajeMapper(PeliculaMapper peliculaMapper){
        this.peliculaMapper = peliculaMapper;
    }

    public PersonajeEntity personajeDTO2Entity (PersonajeDTO dto){
        PersonajeEntity entity = new PersonajeEntity();
        entity.setImagen(dto.getImagen());
        entity.setNombre(dto.getNombre());
        entity.setEdad(dto.getEdad());
        entity.setPeso(dto.getPeso());
        entity.setHistoria(dto.getHistoria());
        return entity;
    }

    public PersonajeDTO personajeEntity2DTO (PersonajeEntity entity, boolean loadPeliculas){
        PersonajeDTO dto = new PersonajeDTO();
        dto.setId(entity.getId());
        dto.setImagen(entity.getImagen());
        dto.setNombre(entity.getNombre());
        dto.setEdad(entity.getEdad());
        dto.setPeso(entity.getPeso());
        dto.setHistoria(entity.getHistoria());
        if(loadPeliculas){
            Set<PeliculaDTO> peliculaDTO = this.peliculaMapper.peliculaEntitySet2DTOList(entity.getPeliculas(),false);
            dto.setPeliculas(peliculaDTO);
        }
        return dto;
    }

    public void personajeEntityRefreshValues(PersonajeEntity entity, PersonajeDTO personajeDTO){
        entity.setImagen(personajeDTO.getImagen());
        entity.setNombre(personajeDTO.getNombre());
        entity.setEdad(personajeDTO.getEdad());
        entity.setPeso(personajeDTO.getPeso());
        entity.setHistoria(personajeDTO.getHistoria());
    }

    public Set<PersonajeEntity> personajeDTOList2Entity (List<PersonajeDTO> dtos){
        Set<PersonajeEntity> entities = new HashSet<>();
        for(PersonajeDTO dto: dtos){
            entities.add(personajeDTO2Entity(dto));
        }
        return entities;
    }

    public Set<PersonajeDTO> personajeEntitySet2DTOList (Collection<PersonajeEntity> entities, boolean loadPeliculas){
        Set<PersonajeDTO> dtos = new HashSet<>();
        for(PersonajeEntity entity: entities){
            dtos.add(personajeEntity2DTO(entity, loadPeliculas));
        }
        return dtos;
    }

    public List<PersonajeBasicDTO> personajeEntitySet2BasicDTOList (Collection<PersonajeEntity> entities){
        List<PersonajeBasicDTO> dtos = new ArrayList<>();
        PersonajeBasicDTO basicDTO;
        for(PersonajeEntity entity: entities){
            basicDTO = new PersonajeBasicDTO();
            basicDTO.setId(entity.getId());
            basicDTO.setImagen(entity.getImagen());
            basicDTO.setNombre(entity.getNombre());
            dtos.add(basicDTO);
        }
        return dtos;

    }
}
