package ru.javarush.textadventure.model;

import java.util.List;

public record GameStep(String question, List<ChoiceOption> options) {

    public String getQuestion() {
        return question;
    }

    public List<ChoiceOption> getOptions() {
        return options;
    }
}
