package ru.javarush.textadventure.model;

public record ChoiceOption(GameChoice choice, String label) {

    public GameChoice getChoice() {
        return choice;
    }

    public String getLabel() {
        return label;
    }
}
