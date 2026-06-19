package ru.javarush.textadventure.web;

import ru.javarush.textadventure.model.GameSession;
import ru.javarush.textadventure.model.GameResult;
import ru.javarush.textadventure.model.GameState;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RestartServlet", urlPatterns = "/restart")
public class RestartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GameSession gameSession = SessionSupport.getGameSession(request);
        if (gameSession == null) {
            response.sendRedirect(request.getContextPath() + "/welcome");
            return;
        }

        gameSession.setGamesPlayed(gameSession.getGamesPlayed() + 1);
        gameSession.setCurrentState(GameState.START);
        gameSession.setGameResult(GameResult.IN_PROGRESS);
        response.sendRedirect(request.getContextPath() + "/game");
    }
}
