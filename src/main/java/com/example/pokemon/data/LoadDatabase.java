package com.example.pokemon.data;

import com.example.pokemon.service.PokemonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(PokemonRepository repository) {

        return args -> {
            PokemonService service = new PokemonService(repository);
            service.getOriginalPokemon();
        };
    }
}