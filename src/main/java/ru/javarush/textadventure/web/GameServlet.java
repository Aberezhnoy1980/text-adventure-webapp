package ru.javarush.textadventure.web;

import ru.javarush.textadventure.model.GameSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GameServlet", urlPatterns = "/game")
public class GameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GameSession gameSession = SessionSupport.getGameSession(request);
        if (gameSession == null || gameSession.getPlayerName() == null || gameSession.getPlayerName().isBlank()) {
            response.sendRedirect(request.getContextPath() + "/welcome");
            return;
        }

        request.setAttribute("clientIp", request.getRemoteAddr());
        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GameSession gameSession = SessionSupport.getGameSession(request);
        if (gameSession == null || gameSession.getPlayerName() == null || gameSession.getPlayerName().isBlank()) {
            response.sendRedirect(request.getContextPath() + "/welcome");
            return;
        }

        request.setAttribute("clientIp", request.getRemoteAddr());
        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }
}
