package com.backend.filters;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.backend.dal.dto.UserAccess;
import com.backend.services.authuser.jwt.JwtToken;
import com.backend.services.hash.HashService;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Singleton
public class AuthJwtFilter implements Filter {
    private FilterConfig filterConfig;

    private final JwtToken jwtTokenService;

    @Inject
    public AuthJwtFilter(JwtToken jwtTokenService) {

        this.jwtTokenService = jwtTokenService;

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain next)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sreq;
        checkJwt(req);
        next.doFilter(req, sresp);
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

    private void checkJwt(HttpServletRequest req) {

        String authHeader = req.getHeader("Authorization");
        if (authHeader == null) {
            req.setAttribute("AuthStatus", "Authorization header required");
            return;

        }

        String authScheme = "Bearer ";

        if (!authHeader.startsWith(authScheme)) {
            req.setAttribute("AuthStatus", "Authorization cheme error ");
            return;

        }

        String credentials = authHeader.substring(authScheme.length());

        if (jwtTokenService.fromJwt(credentials) == null) {

            req.setAttribute("AuthStatus", "Token error");
            return;

        }

        String payload = jwtTokenService.fromJwt(credentials);

        if (JsonParser.parseString(payload).getAsJsonObject().get("exp").getAsLong() < new Date().getTime()) {
            req.setAttribute("AuthStatus", "Token exp fail");
            return;

        }

        UserAccess userAccess = new Gson().fromJson(payload, UserAccess.class);
        req.setAttribute("AuthStatus", "OK");
        req.setAttribute("AuthUserAccess", userAccess);
    }
}
