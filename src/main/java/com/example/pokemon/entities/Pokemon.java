package com.example.pokemon.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {

    private @Id @GeneratedValue Long id;
    private String name;
    private String type;
    private String image;
    private String pokedexNumber;

    public Pokemon(String name, String type, String image, String pokedexNumber) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.pokedexNumber = pokedexNumber;
    }

    public Pokemon() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        Pokemon pokemon = (Pokemon) o;
        return Objects.equals(id, pokemon.id) && Objects.equals(name, pokemon.name) && Objects.equals(type, pokemon.type) && Objects.equals(image, pokemon.image) && Objects.equals(pokedexNumber, pokemon.pokedexNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, image, pokedexNumber);
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                ", pokedexNumber='" + pokedexNumber + '\'' +
                '}';
    }
}
