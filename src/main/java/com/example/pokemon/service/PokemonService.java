package com.example.pokemon.service;

import com.example.pokemon.data.PokemonRepository;
import com.example.pokemon.entities.Pokemon;
import com.example.pokemon.utils.GeneralUtils;
import com.example.pokemon.utils.PokemonApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PokemonService {

    private final PokemonRepository repository;
    private static final int NUMBER_OF_ORIGINAL_POKEMON = 151;

    private static final Logger log = LoggerFactory.getLogger(PokemonService.class);


    public PokemonService(PokemonRepository repository) {
        this.repository = repository;
    }

    private static CompletableFuture<String> getPokemonById(int id) throws URISyntaxException {

        final URI requestUri = new URI("https://pokeapi.co/api/v2/pokemon/" + id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(requestUri)
                .header("Accept", "application/json")
                .GET()
                .build();
        return HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }

    private Pokemon mapResponseToPokemon(CompletableFuture<String> body) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, Object> map
                    = objectMapper.readValue(body.get(), new TypeReference<Map<String, Object>>() {
            });
            var name = map.get("name").toString();
            var pokedexNumber = GeneralUtils.formatPokedexNumber(map.get("id").toString());
            var type = map.get("types").toString().split("name=")[1].split(",")[0];
            var imageUrl = GeneralUtils.getImageFromPokedexNumber(pokedexNumber);
            var pokemon = new Pokemon(name, type, imageUrl, "#" + pokedexNumber);
            log.info("Saved " + pokemon.toString());
            return repository.save(pokemon);
        } catch (JsonProcessingException e) {
            throw new PokemonApiException();
        } catch (InterruptedException e) {
            throw new PokemonApiException();
        } catch (ExecutionException e) {
            throw new PokemonApiException();
        }
    }

    public List<Pokemon> getOriginalPokemon() {
        int counter = 1;
        List<Pokemon> originalPokemon = new ArrayList<>();
        while (counter <= NUMBER_OF_ORIGINAL_POKEMON) {
            try {
                var request = getPokemonById(counter);
                originalPokemon.add(mapResponseToPokemon(request));
            } catch (Exception e) {
                throw new PokemonApiException();
            }
            counter++;
        }
        return originalPokemon;
    }

}
