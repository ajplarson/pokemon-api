package com.example.pokemon.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class PokemonRequestObject {
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("pokedexNumber")
    private String pokedexNumber;

    public PokemonRequestObject() {

    }

    public PokemonRequestObject(String name, String type, String pokedexNumber) {
        this.name = name;
        this.type = type;
        this.pokedexNumber = pokedexNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPokedexNumber() {
        return pokedexNumber;
    }

    public void setPokedexNumber(String pokedexNumber) {
        this.pokedexNumber = pokedexNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokemonRequestObject that = (PokemonRequestObject) o;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(pokedexNumber, that.pokedexNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, pokedexNumber);
    }

    @Override
    public String toString() {
        return "PokemonRequestObject{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", pokedexNumber='" + pokedexNumber + '\'' +
                '}';
    }
}