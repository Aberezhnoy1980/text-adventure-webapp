package ru.javarush.textadventure.model;

public class ChoiceOption {

    private final GameChoice choice;
    private final String label;

    public ChoiceOption(GameChoice choice, String label) {
        this.choice = choice;
        this.label = label;
    }

    public GameChoice getChoice() {
        return choice;
    }

    public String getLabel() {
        return label;
    }
}
