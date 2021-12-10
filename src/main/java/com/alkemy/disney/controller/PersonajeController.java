package com.alkemy.disney.controller;

import com.alkemy.disney.dto.PersonajeBasicDTO;
import com.alkemy.disney.dto.PersonajeDTO;
import com.alkemy.disney.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("personajes")
public class PersonajeController {

    private PersonajeService personajeService;

    @Autowired
    public PersonajeController(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<PersonajeBasicDTO>> getAll() {
        List<PersonajeBasicDTO> icons = this.personajeService.getAll();
        return ResponseEntity.ok(icons);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PersonajeDTO> getDetailsById(@PathVariable Long id) {
        PersonajeDTO icon = this.personajeService.getDetailsById(id);
        return ResponseEntity.ok(icon);
    }


    @GetMapping
    public ResponseEntity<List<PersonajeDTO>> getDetailsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long age,
            @RequestParam(required = false) Set<Long> movies,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        List<PersonajeDTO> icons = this.personajeService.getByFilters(name, age, movies, order);
        return ResponseEntity.ok(icons);
    }


    @PostMapping
    public ResponseEntity<PersonajeDTO> save(@RequestBody PersonajeDTO icon) {
        PersonajeDTO result = this.personajeService.save(icon);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PersonajeDTO> update(@PathVariable Long id, @RequestBody PersonajeDTO personajeDTO) {
        PersonajeDTO result = this.personajeService.update(id, personajeDTO);
        return ResponseEntity.ok().body(result);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.personajeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/{id}/pelicula/{idPelicula}")
    public ResponseEntity<Void> addPais(@PathVariable Long id, @PathVariable Long idPelicula) {
        this.personajeService.addPelicula(id, idPelicula);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @DeleteMapping("/{id}/pelicula/{idPelicula}")
    public ResponseEntity<Void> removePais(@PathVariable Long id, @PathVariable Long idPelicula) {
        this.personajeService.removePelicula(id, idPelicula);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}



    /*
    //TODO: ESTO ES EL icon controller para tomar como base para más adelante!!


     */

