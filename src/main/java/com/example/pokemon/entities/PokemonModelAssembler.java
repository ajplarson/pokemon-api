package com.example.pokemon.entities;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.pokemon.controllers.PokemonController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public
class PokemonModelAssembler implements RepresentationModelAssembler<Pokemon, EntityModel<Pokemon>> {

    @Override
    public EntityModel<Pokemon> toModel(Pokemon pokemon) {

        return EntityModel.of(pokemon, //
                linkTo(methodOn(PokemonController.class).one(pokemon.getId())).withSelfRel(),
                linkTo(methodOn(PokemonController.class).all()).withRel("pokemon"));
    }
}
