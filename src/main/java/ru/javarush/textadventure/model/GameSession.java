package ru.javarush.textadventure.model;

import java.io.Serializable;

public class GameSession implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SESSION_ATTRIBUTE = "gameSession";

    private String playerName;
    private int gamesPlayed;
    private GameState currentState = GameState.START;
    private GameResult gameResult = GameResult.IN_PROGRESS;

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
}
