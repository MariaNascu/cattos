package com.company.cattos.security.jwt.filter;

import com.company.cattos.exception.CattosException;
import com.company.cattos.security.jwt.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private static final List<String> shouldNotFilter = List.of(
            "/h2-console",
            "/h2-console/**",
            "/favicon.ico",
            "/api/club/findAll",
            "/api/request/findAll",
            "/api/auth/token/register",
            "/api/auth/token/login");

    private final JWTService jwtService;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JWTAuthenticationFilter(@Lazy JWTService jwtService, @Lazy UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }
    private static final String BEARER_PRE = "Bearer ";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        Optional<String> jwtToken = getTokenFromRequest(request);
        if (jwtToken.isPresent()) {
            String token = jwtToken.get();
            var isValid = jwtService.verifyToken(token);
            if (isValid) {
                String username = jwtService.extractAllClaims(token.describeConstable()).get("email").toString();

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                throw CattosException.of("The token is not valid");
            }
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return Optional.ofNullable(header)
                .filter(token -> token.startsWith(BEARER_PRE))
                .map(token -> token.substring(BEARER_PRE.length()));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return shouldNotFilter.stream().anyMatch(path::startsWith);
    }
}
