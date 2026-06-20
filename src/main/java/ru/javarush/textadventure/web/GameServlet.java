package ru.javarush.textadventure.web;

import ru.javarush.textadventure.model.GameSession;
import ru.javarush.textadventure.service.GameService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GameServlet", urlPatterns = "/game")
public class GameServlet extends HttpServlet {

    private final GameService gameService = new GameService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!SessionSupport.hasRegisteredPlayer(request)) {
            response.sendRedirect(request.getContextPath() + "/welcome");
            return;
        }

        GameSession gameSession = SessionSupport.getRegisteredPlayer(request);
        forwardGamePage(request, response, gameSession, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!SessionSupport.hasRegisteredPlayer(request)) {
            response.sendRedirect(request.getContextPath() + "/welcome");
            return;
        }

        GameSession gameSession = SessionSupport.getRegisteredPlayer(request);
        String errorMessage = gameService.submitChoice(gameSession, request.getParameter("choice"));
        forwardGamePage(request, response, gameSession, errorMessage);
    }

    private void forwardGamePage(HttpServletRequest request, HttpServletResponse response,
                               GameSession gameSession, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute("gameView", gameService.createView(gameSession, request.getRemoteAddr(), errorMessage));
        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }
}
