package ru.javarush.textadventure.model;

import java.util.List;

public class GameView {

    private final String question;
    private final List<ChoiceOption> options;
    private final boolean finished;
    private final String clientIp;
    private final String errorMessage;

    public GameView(GameStep step, boolean finished, String clientIp, String errorMessage) {
        this.question = step.getQuestion();
        this.options = step.getOptions();
        this.finished = finished;
        this.clientIp = clientIp;
        this.errorMessage = errorMessage;
    }

    public String getQuestion() {
        return question;
    }

    public List<ChoiceOption> getOptions() {
        return options;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getClientIp() {
        return clientIp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
