package ru.javarush.textadventure.service;

import ru.javarush.textadventure.model.GameSession;
import ru.javarush.textadventure.model.GameView;

import java.util.Objects;

public class GameService {

    private final GameEngine gameEngine = new GameEngine();

    public GameView createView(GameSession session, String clientIp, String errorMessage) {
        Objects.requireNonNull(session, "session");
        return new GameView(
                gameEngine.getCurrentStep(session),
                gameEngine.isGameFinished(session),
                clientIp,
                errorMessage
        );
    }

    public String submitChoice(GameSession session, String rawChoice) {
        return gameEngine.submitChoice(session, rawChoice);
    }
}
