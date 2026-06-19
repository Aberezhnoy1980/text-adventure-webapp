package ru.javarush.textadventure.web;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter(filterName = "CharacterEncodingFilter", urlPatterns = "/*")
public class CharacterEncodingFilter implements Filter {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // POST-параметры (имя игрока) декодируются контейнером только после setCharacterEncoding.
        request.setCharacterEncoding(UTF_8);
        chain.doFilter(request, response);
    }
}
