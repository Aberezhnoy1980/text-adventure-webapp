package ru.javarush.textadventure.model;

import java.io.Serializable;

public class GameSession implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SESSION_ATTRIBUTE = "gameSession";

    private String playerName;
    private int gamesPlayed;
    private GameState currentState = GameState.START;
    private GameResult gameResult = GameResult.IN_PROGRESS;
    private boolean escortArrived;
    private String outcomeMessage;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

    public boolean isEscortArrived() {
        return escortArrived;
    }

    public void setEscortArrived(boolean escortArrived) {
        this.escortArrived = escortArrived;
    }

    public String getOutcomeMessage() {
        return outcomeMessage;
    }

    public void setOutcomeMessage(String outcomeMessage) {
        this.outcomeMessage = outcomeMessage;
    }

    public void resetForNewGame() {
        currentState = GameState.START;
        gameResult = GameResult.IN_PROGRESS;
        escortArrived = false;
        outcomeMessage = null;
    }
}
