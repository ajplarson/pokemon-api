package com.example.pokemon.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.example.pokemon.data.PokemonRepository;
import com.example.pokemon.entities.Pokemon;
import com.example.pokemon.entities.PokemonModelAssembler;
import com.example.pokemon.entities.PokemonRequestObject;
import com.example.pokemon.utils.GeneralUtils;
import com.example.pokemon.utils.PokemonNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public
class PokemonController {

    private final PokemonRepository repository;
    private final PokemonModelAssembler assembler;

    private static final Logger log = LoggerFactory.getLogger(PokemonController.class);


    PokemonController(PokemonRepository repository, PokemonModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/api/pokemon")
    public CollectionModel<EntityModel<Pokemon>> all() {
        log.info("GET ALL");
        List<EntityModel<Pokemon>> pokemons = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pokemons, linkTo(methodOn(PokemonController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/api/pokemon")
    ResponseEntity<?> newPokemon(@RequestBody PokemonRequestObject requestBody) {
        log.info("POST");
        var pokedexNumber = GeneralUtils.formatPokedexNumber(requestBody.getPokedexNumber());
        var imageUrl = GeneralUtils.getImageFromPokedexNumber(pokedexNumber);
        Pokemon newPokemon = new Pokemon(requestBody.getName(), requestBody.getType(), imageUrl, "#"+pokedexNumber);
        EntityModel<Pokemon> entityModel = assembler.toModel(repository.save(newPokemon));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    // Single item

    @GetMapping("/api/pokemon/{id}")
    public EntityModel<Pokemon> one(@PathVariable Long id) {
        log.info("GET ONE");
        Pokemon pokemon = repository.findById(id)
                .orElseThrow(() -> new PokemonNotFoundException(id));
        return assembler.toModel(pokemon);
    }

    @PutMapping("/api/pokemon/{id}")
    ResponseEntity<?> update(@RequestBody Pokemon newPokemon, @PathVariable Long id) {
        Pokemon updatedPokemon = repository.findById(id) //
                .map(pokemon -> {
                    pokemon.setName(newPokemon.getName());
                    pokemon.setImage(newPokemon.getImage());
                    pokemon.setType(newPokemon.getType());
                    pokemon.setPokedexNumber(newPokemon.getPokedexNumber());
                    return repository.save(pokemon);
                }) //
                .orElseGet(() -> {
                    newPokemon.setId(id);
                    return repository.save(newPokemon);
                });

        EntityModel<Pokemon> entityModel = assembler.toModel(updatedPokemon);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/api/pokemon/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}