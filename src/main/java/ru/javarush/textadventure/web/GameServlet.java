package ru.javarush.textadventure.web;

import ru.javarush.textadventure.model.GameChoice;
import ru.javarush.textadventure.model.GameSession;
import ru.javarush.textadventure.model.GameStep;
import ru.javarush.textadventure.service.GameEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "GameServlet", urlPatterns = "/game")
public class GameServlet extends HttpServlet {

    private final GameEngine gameEngine = new GameEngine();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GameSession gameSession = requireGameSession(request, response);
        if (gameSession == null) {
            return;
        }

        forwardToGameView(request, response, gameSession);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GameSession gameSession = requireGameSession(request, response);
        if (gameSession == null) {
            return;
        }

        if (!gameEngine.isGameFinished(gameSession)) {
            Optional<GameChoice> choice = gameEngine.parseChoice(request.getParameter("choice"));
            if (choice.isEmpty()) {
                request.setAttribute("errorMessage", "Выберите вариант ответа.");
            } else if (!gameEngine.isValidChoice(gameSession.getCurrentState(), choice.get())) {
                request.setAttribute("errorMessage", "Выберите вариант ответа.");
            } else {
                gameEngine.applyChoice(gameSession, choice.get());
            }
        }

        forwardToGameView(request, response, gameSession);
    }

    private GameSession requireGameSession(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        GameSession gameSession = SessionSupport.getGameSession(request);
        if (gameSession == null || gameSession.getPlayerName() == null || gameSession.getPlayerName().isBlank()) {
            response.sendRedirect(request.getContextPath() + "/welcome");
            return null;
        }
        return gameSession;
    }

    private void forwardToGameView(HttpServletRequest request, HttpServletResponse response, GameSession gameSession)
            throws ServletException, IOException {
        GameStep currentStep = gameEngine.getCurrentStep(gameSession);
        request.setAttribute("currentStep", currentStep);
        request.setAttribute("gameFinished", gameEngine.isGameFinished(gameSession));
        request.setAttribute("clientIp", request.getRemoteAddr());
        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }
}
