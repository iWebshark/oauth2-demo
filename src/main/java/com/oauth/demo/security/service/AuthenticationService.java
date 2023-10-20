package com.oauth.demo.security.service;

import com.oauth.demo.controller.dto.SignInRequest;
import com.oauth.demo.controller.dto.SignUpRequest;
import com.oauth.demo.model.User;
import com.oauth.demo.persistence.model.OAuth2Provider;
import com.oauth.demo.persistence.model.Role;
import com.oauth.demo.security.TokenProvider;
import com.oauth.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    public String signUp(SignUpRequest request) {
        if (userService.hasUserWithEmail(request.getUsername())) {
            throw new BadCredentialsException("User with this username already registered!");
        }
        if (userService.hasUserWithEmail(request.getEmail())) {
            throw new BadCredentialsException("User with this email already registered!");
        }

        userService.saveUser(convertToUser(request));
        return authenticateAndGetToken(request.getEmail(), request.getPassword());
    }

    public String signIn(SignInRequest request) {
        return authenticateAndGetToken(request.getUsername(), request.getPassword());
    }

    private String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generateToken(authentication);
    }

    private User convertToUser(SignUpRequest signUpRequest) {
        return User.builder()
                .username(signUpRequest.getUsername())
                .firstname(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.ADMIN)
                .provider(OAuth2Provider.LOCAL)
                .build();
    }
}
