package com.example.Task.jwt;

import com.example.Task.exception.UnauthorizedException;
import com.example.Task.service.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserServiceImpl userServiceImpl;
    @Override
    protected void doFilterInternal(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        FilterChain filterChain) throws ServletException, IOException {

        final String jwt = httpServletRequest.getHeader(AUTHORIZATION);

        if (jwt == null || !jwt.startsWith("Bearer")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        final String jwtToken = jwtUtils.parseJwt(jwt);
        try {
            jwtUtils.validateJwtToken(jwtToken);
            setUpAuthentication(httpServletRequest, jwtToken);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        }
    }

    private void setUpAuthentication(HttpServletRequest httpServletRequest, String jwt) {
        String username = jwtUtils.getUsernameFromToken(jwt);
        UserDetails user = userServiceImpl.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}


