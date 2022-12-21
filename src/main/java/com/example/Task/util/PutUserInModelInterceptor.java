package com.example.Task.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class PutUserInModelInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest aRequest, HttpServletResponse aResponse, Object aHandler) {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest aRequest,
                           HttpServletResponse aResponse,
                           Object aHandler,
                           ModelAndView aModelAndView) {

        if (aModelAndView != null) {
            aModelAndView
                .addObject("__user", aRequest.getUserPrincipal())
                .addObject("__adminRole", aRequest.isUserInRole("ROLE_ADMIN"));
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest aRequest,
                                HttpServletResponse aResponse,
                                Object aHandler,
                                Exception aEx) {
    }
}
