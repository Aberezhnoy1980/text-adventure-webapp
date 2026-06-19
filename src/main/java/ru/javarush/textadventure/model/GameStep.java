package ru.javarush.textadventure.model;

import java.util.List;

public class GameStep {

    private final String question;
    private final List<ChoiceOption> options;

    public GameStep(String question, List<ChoiceOption> options) {
        this.question = question;
        this.options = List.copyOf(options);
    }

    public String getQuestion() {
        return question;
    }

    public List<ChoiceOption> getOptions() {
        return options;
    }
}
