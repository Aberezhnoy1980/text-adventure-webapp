package ru.javarush.textadventure.web;

import ru.javarush.textadventure.model.GameSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class SessionSupport {

    private SessionSupport() {
    }

    public static GameSession getGameSession(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        if (httpSession == null) {
            return null;
        }
        Object attribute = httpSession.getAttribute(GameSession.SESSION_ATTRIBUTE);
        if (attribute instanceof GameSession gameSession) {
            return gameSession;
        }
        return null;
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
}
