package com.alkemy.disney.dto;

import com.alkemy.disney.entities.PeliculaEntity;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
public class GeneroDTO {

    private Long id;
    private String nombre;
    private String imagen;
    private Set<PeliculaEntity> peliculas;

}
