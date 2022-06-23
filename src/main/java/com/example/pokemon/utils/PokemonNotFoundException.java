package com.example.pokemon.utils;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(Long id) {
        super("Could not find pokemon " + id);
    }
}