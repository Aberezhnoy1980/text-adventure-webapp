package ru.javarush.textadventure.web;

import ru.javarush.textadventure.model.GameSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public final class SessionSupport {

    private SessionSupport() {
    }

    public static Optional<GameSession> findGameSession(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        if (httpSession == null) {
            return Optional.empty();
        }

        Object attribute = httpSession.getAttribute(GameSession.SESSION_ATTRIBUTE);
        if (attribute instanceof GameSession gameSession) {
            return Optional.of(gameSession);
        }
        return Optional.empty();
    }

    public static boolean hasRegisteredPlayer(HttpServletRequest request) {
        return findGameSession(request)
                .map(SessionSupport::hasPlayerName)
                .orElse(false);
    }

    public static GameSession getRegisteredPlayer(HttpServletRequest request) {
        return findGameSession(request)
                .filter(SessionSupport::hasPlayerName)
                .orElseThrow(() -> new IllegalStateException("Registered player session is required"));
    }

    public static GameSession getOrCreateGameSession(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(true);
        Object attribute = httpSession.getAttribute(GameSession.SESSION_ATTRIBUTE);
        if (attribute instanceof GameSession gameSession) {
            return gameSession;
        }

        GameSession gameSession = new GameSession();
        httpSession.setAttribute(GameSession.SESSION_ATTRIBUTE, gameSession);
        return gameSession;
    }

    private static boolean hasPlayerName(GameSession session) {
        String playerName = session.getPlayerName();
        return playerName != null && !playerName.isBlank();
    }
}
