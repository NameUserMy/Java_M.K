package com.backend.filters;

import java.io.IOException;

import com.backend.dal.dao.DataContext;
import com.backend.dal.dto.UserAccess;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Singleton
public class AuthFilter implements Filter {
    private FilterConfig filterConfig;
    private DataContext dataContext;

    @Inject
    public AuthFilter(DataContext dataContext) {
        
        this.dataContext = dataContext;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain next)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) sreq;
    
        String authStatus;

        String authHeader = req.getHeader("Authorization");
        if (authHeader == null) {
            authStatus = "Authorization header required";

        } else {
            String authScheme = "Bearer ";

            if (!authHeader.startsWith(authScheme)) {
                authStatus = "Authorization cheme error ";

            }

            String credentials = authHeader.substring(authScheme.length());

            UserAccess userAccess = dataContext.getAccessTokenDao().getUserAccess(credentials);

            if (userAccess == null) {

                authStatus = "Token expires or invalid ";

            } else {

                authStatus = "OK";
                req.setAttribute("AuthUserAccess", userAccess);

            }

        }
        req.setAttribute("AuthStatus", authStatus);
        next.doFilter(req, sresp);

    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
