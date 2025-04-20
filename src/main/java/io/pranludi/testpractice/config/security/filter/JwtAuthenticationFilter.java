package io.pranludi.testpractice.config.security.filter;

import io.pranludi.testpractice.config.security.common.TokenExtractor;
import io.pranludi.testpractice.config.security.service.JwtAuthenticationService;
import io.pranludi.testpractice.exception.JwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationService jwtAuthenticationService;
    private final TokenExtractor tokenExtractor;

    public JwtAuthenticationFilter(JwtAuthenticationService jwtAuthenticationService, TokenExtractor tokenExtractor) {
        this.jwtAuthenticationService = jwtAuthenticationService;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        try {
            tokenExtractor.extractFromHeader(authHeader)
                .ifPresent(token -> {
                    Authentication authentication = jwtAuthenticationService.authenticateToken(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });

            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        }
    }

}

