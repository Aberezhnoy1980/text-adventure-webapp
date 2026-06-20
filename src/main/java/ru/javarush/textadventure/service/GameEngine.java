package ru.javarush.textadventure.service;

import ru.javarush.textadventure.model.ChoiceOption;
import ru.javarush.textadventure.model.GameChoice;
import ru.javarush.textadventure.model.GameResult;
import ru.javarush.textadventure.model.GameSession;
import ru.javarush.textadventure.model.GameState;
import ru.javarush.textadventure.model.GameStep;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class GameEngine {

    private static final String CHOICE_REQUIRED_MESSAGE = "Выберите вариант ответа.";

    public GameStep getCurrentStep(GameSession session) {
        Objects.requireNonNull(session, "session");

        if (isGameFinished(session)) {
            return new GameStep(session.getOutcomeMessage(), List.of());
        }

        return switch (session.getCurrentState()) {
            case START -> step(
                    "Ты потерял память? Принять вызов НЛО?",
                    GameChoice.ACCEPT, GameChoice.REJECT, GameChoice.INQUIRE
            );
            case INQUIRY -> step(
                    "Голос с мостика: «Мы ищем того, кто потерял след в туманности. "
                            + "Ваш ход, командир».",
                    GameChoice.ACCEPT, GameChoice.REJECT
            );
            case ACCEPTED_CHALLENGE -> step(
                    "Ты принял вызов. Поднимешься на мостик к их капитану?",
                    GameChoice.GO_TO_BRIDGE, GameChoice.REFUSE_BRIDGE, GameChoice.REQUEST_ESCORT
            );
            case ESCORT_APPROACH -> step(
                    "Капитан чужого корабля назначил escort. Идёшь к мостику под охраной?",
                    GameChoice.PROCEED_WITH_ESCORT, GameChoice.REFUSE_ESCORT
            );
            case ON_BRIDGE -> onBridgeStep(session);
            case VICTORY, DEFEAT -> new GameStep(session.getOutcomeMessage(), List.of());
        };
    }

    public String submitChoice(GameSession session, String rawValue) {
        Objects.requireNonNull(session, "session");

        if (isGameFinished(session)) {
            return null;
        }

        GameChoice choice = resolveChoice(rawValue, session.getCurrentState());
        if (choice == null) {
            return CHOICE_REQUIRED_MESSAGE;
        }

        applyChoice(session, choice);
        return null;
    }

    public void applyChoice(GameSession session, GameChoice choice) {
        Objects.requireNonNull(session, "session");
        Objects.requireNonNull(choice, "choice");

        if (isGameFinished(session)) {
            throw new IllegalStateException("Game is already finished");
        }
        if (!isValidChoice(session.getCurrentState(), choice)) {
            throw new IllegalArgumentException("Choice " + choice + " is not valid for state " + session.getCurrentState());
        }

        switch (session.getCurrentState()) {
            case START -> applyStartChoice(session, choice);
            case INQUIRY -> applyInquiryChoice(session, choice);
            case ACCEPTED_CHALLENGE -> applyAcceptedChallengeChoice(session, choice);
            case ESCORT_APPROACH -> applyEscortApproachChoice(session, choice);
            case ON_BRIDGE -> applyOnBridgeChoice(session, choice);
            default -> throw new IllegalStateException("Unexpected state: " + session.getCurrentState());
        }
    }

    public boolean isGameFinished(GameSession session) {
        Objects.requireNonNull(session, "session");
        return session.getGameResult() != GameResult.IN_PROGRESS;
    }

    private GameChoice resolveChoice(String rawValue, GameState state) {
        if (rawValue == null || rawValue.isBlank()) {
            return null;
        }

        try {
            GameChoice choice = GameChoice.valueOf(rawValue.trim());
            return isValidChoice(state, choice) ? choice : null;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private boolean isValidChoice(GameState state, GameChoice choice) {
        return getValidChoices(state).contains(choice);
    }

    private Set<GameChoice> getValidChoices(GameState state) {
        return switch (state) {
            case START -> EnumSet.of(GameChoice.ACCEPT, GameChoice.REJECT, GameChoice.INQUIRE);
            case INQUIRY -> EnumSet.of(GameChoice.ACCEPT, GameChoice.REJECT);
            case ACCEPTED_CHALLENGE -> EnumSet.of(
                    GameChoice.GO_TO_BRIDGE, GameChoice.REFUSE_BRIDGE, GameChoice.REQUEST_ESCORT
            );
            case ESCORT_APPROACH -> EnumSet.of(GameChoice.PROCEED_WITH_ESCORT, GameChoice.REFUSE_ESCORT);
            case ON_BRIDGE -> EnumSet.of(GameChoice.TELL_TRUTH, GameChoice.LIE, GameChoice.STAY_SILENT);
            case VICTORY, DEFEAT -> EnumSet.noneOf(GameChoice.class);
        };
    }

    private void applyStartChoice(GameSession session, GameChoice choice) {
        switch (choice) {
            case ACCEPT -> session.setCurrentState(GameState.ACCEPTED_CHALLENGE);
            case REJECT -> finishDefeat(session, "Ты отклонил вызов. Поражение.");
            case INQUIRE -> session.setCurrentState(GameState.INQUIRY);
            default -> throw new IllegalArgumentException("Unexpected choice: " + choice);
        }
    }

    private void applyInquiryChoice(GameSession session, GameChoice choice) {
        switch (choice) {
            case ACCEPT -> session.setCurrentState(GameState.ACCEPTED_CHALLENGE);
            case REJECT -> finishDefeat(session, "Ты отклонил вызов. Поражение.");
            default -> throw new IllegalArgumentException("Unexpected choice: " + choice);
        }
    }

    private void applyAcceptedChallengeChoice(GameSession session, GameChoice choice) {
        switch (choice) {
            case GO_TO_BRIDGE -> {
                session.setEscortArrived(false);
                session.setCurrentState(GameState.ON_BRIDGE);
            }
            case REFUSE_BRIDGE -> finishDefeat(session, "Ты не пошёл на переговоры. Поражение.");
            case REQUEST_ESCORT -> session.setCurrentState(GameState.ESCORT_APPROACH);
            default -> throw new IllegalArgumentException("Unexpected choice: " + choice);
        }
    }

    private void applyEscortApproachChoice(GameSession session, GameChoice choice) {
        switch (choice) {
            case PROCEED_WITH_ESCORT -> {
                session.setEscortArrived(true);
                session.setCurrentState(GameState.ON_BRIDGE);
            }
            case REFUSE_ESCORT -> finishDefeat(session, "Отказ от escort воспринят как враждебность. Поражение.");
            default -> throw new IllegalArgumentException("Unexpected choice: " + choice);
        }
    }

    private void applyOnBridgeChoice(GameSession session, GameChoice choice) {
        switch (choice) {
            case TELL_TRUTH -> finishVictory(session, "Тебя вернули домой. Победа.");
            case LIE -> finishDefeat(session, "Твою ложь разоблачили. Поражение.");
            case STAY_SILENT -> finishDefeat(session, "Тишину сочли за отказ от сотрудничества. Поражение.");
            default -> throw new IllegalArgumentException("Unexpected choice: " + choice);
        }
    }

    private void finishVictory(GameSession session, String message) {
        session.setCurrentState(GameState.VICTORY);
        session.setGameResult(GameResult.VICTORY);
        session.setOutcomeMessage(message);
    }

    private void finishDefeat(GameSession session, String message) {
        session.setCurrentState(GameState.DEFEAT);
        session.setGameResult(GameResult.DEFEAT);
        session.setOutcomeMessage(message);
    }

    private GameStep onBridgeStep(GameSession session) {
        String question = session.isEscortArrived()
                ? "Escort довёл тебя до мостика. Капитан смотрит внимательно: «Ты кто?»"
                : "Ты поднялся на мостик. Капитан смотрит внимательно: «Ты кто?»";
        return step(question, GameChoice.TELL_TRUTH, GameChoice.LIE, GameChoice.STAY_SILENT);
    }

    private GameStep step(String question, GameChoice... choices) {
        List<ChoiceOption> options = List.of(choices).stream()
                .map(choice -> new ChoiceOption(choice, choice.getLabel()))
                .toList();
        return new GameStep(question, options);
    }
}
