package com.example.pokemon.utils;

public class PokemonApiException extends RuntimeException {
    public PokemonApiException() {
        super("Error mapping pokemon from external API");
    }
}
