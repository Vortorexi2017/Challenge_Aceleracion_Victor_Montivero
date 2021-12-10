package com.alkemy.disney.controller;

import com.alkemy.disney.dto.PeliculaBasicDTO;
import com.alkemy.disney.dto.PeliculaDTO;
import com.alkemy.disney.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("peliculas")
public class PeliculaController {

    private PeliculaService peliculaService;

    @Autowired
    public PeliculaController(PeliculaService peliculaService){this.peliculaService = peliculaService;}

    @GetMapping("/all")
    public ResponseEntity<List<PeliculaBasicDTO>> getAll() {
        List<PeliculaBasicDTO> icons = this.peliculaService.getAll();
        return ResponseEntity.ok(icons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeliculaDTO> getDetailsById(@PathVariable Long id) {
        PeliculaDTO icon = this.peliculaService.getDetailsById(id);
        return ResponseEntity.ok(icon);
    }

    @GetMapping
    public ResponseEntity<List<PeliculaDTO>> getDetailsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Set<String> genre,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ){
        List<PeliculaDTO> icons = this.peliculaService.getByFilters(name, genre, order);
        return ResponseEntity.ok(icons);
    }


    @PostMapping
    public ResponseEntity<PeliculaDTO> save(@RequestBody PeliculaDTO icon){
        PeliculaDTO result = this.peliculaService.save(icon);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeliculaDTO> update(@PathVariable Long id, @RequestBody PeliculaDTO peliculaDTO){
        PeliculaDTO result = this.peliculaService.update(id, peliculaDTO);
        return ResponseEntity.ok().body(result);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.peliculaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/personaje/{idPersonaje}")
    public ResponseEntity<Void> addPersonaje(@PathVariable Long id, @PathVariable Long idPersonaje){
        this.peliculaService.addPersonaje(id, idPersonaje);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @DeleteMapping("/{id}/personaje/{idPersonaje}")
    public ResponseEntity<Void> removePersonaje(@PathVariable Long id, @PathVariable Long idPersonaje){
        this.peliculaService.removePersonaje(id, idPersonaje);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
