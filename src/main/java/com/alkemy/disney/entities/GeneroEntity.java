package com.alkemy.disney.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "genero")
public class GeneroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;
    private String imagen;

    @ManyToMany(
            mappedBy = "generos",
            cascade = CascadeType.ALL
    )
    private Set<PeliculaEntity> peliculas = new HashSet<>();
}
