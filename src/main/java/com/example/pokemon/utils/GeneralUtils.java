package com.example.pokemon.utils;

public class GeneralUtils {

    private static int getNumberOfDigits(int number) {
        if (number < 100000) {
            if (number < 100) {
                if (number < 10) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (number < 1000) {
                    return 3;
                } else {
                    if (number < 10000) {
                        return 4;
                    } else {
                        return 5;
                    }
                }
            }
        } else {
            if (number < 10000000) {
                if (number < 1000000) {
                    return 6;
                } else {
                    return 7;
                }
            } else {
                if (number < 100000000) {
                    return 8;
                } else {
                    if (number < 1000000000) {
                        return 9;
                    } else {
                        return 10;
                    }
                }
            }
        }
    }

    public static String getImageFromPokedexNumber(String pokedexNumber) {
        return "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/" + pokedexNumber + ".png";
    }

    public static String formatPokedexNumber(String input) {
        var length = getNumberOfDigits(Integer.valueOf(input).intValue());
        if (length == 1) {
            return "00" + input;
        } else if (length == 2) {
            return "0" + input;
        } else if (length == 3) {
            return input;
        } else {
            return "";
        }
    }
}
