package com.example.pizzeria.service;

import com.example.pizzeria.domain.entity.User;
import com.example.pizzeria.dto.AuthRequest;
import com.example.pizzeria.dto.AuthResponse;
import com.example.pizzeria.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User principal = (User) authentication.getPrincipal();
        log.info("User {} authenticated", principal.getUsername());
        String token = jwtService.generateToken(principal);
        return new AuthResponse(token, principal.getRole().name());
    }
}
