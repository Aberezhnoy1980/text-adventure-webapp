package ru.javarush.textadventure.web;

import ru.javarush.textadventure.model.GameSession;

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
        if (!SessionSupport.hasRegisteredPlayer(request)) {
            response.sendRedirect(request.getContextPath() + "/welcome");
            return;
        }

        GameSession gameSession = SessionSupport.getRegisteredPlayer(request);
        gameSession.setGamesPlayed(gameSession.getGamesPlayed() + 1);
        gameSession.resetForNewGame();
        response.sendRedirect(request.getContextPath() + "/game");
    }
}
