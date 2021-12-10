package com.alkemy.disney.entities;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "personaje")
@SQLDelete(sql = "UPDATE personaje SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class PersonajeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String imagen;
    private String nombre;
    private Long edad;
    private Long peso;
    private String historia;

    private boolean deleted = Boolean.FALSE;

    @ManyToMany(
            mappedBy = "personajes",
            cascade = CascadeType.ALL
    )
    private Set<PeliculaEntity> peliculas = new HashSet<>();


    public void addPelicula(PeliculaEntity pelicula) { this.peliculas.add(pelicula);}

    public void removePelicula(PeliculaEntity pelicula) { this.peliculas.remove(pelicula);}
}
