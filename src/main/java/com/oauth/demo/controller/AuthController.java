package com.oauth.demo.controller;

import com.oauth.demo.controller.dto.LoginRequest;
import com.oauth.demo.controller.dto.SignUpRequest;
import com.oauth.demo.model.User;
import com.oauth.demo.persistence.model.Role;
import com.oauth.demo.security.TokenProvider;
import com.oauth.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @PostMapping
    public String login(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticateAndGetToken(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public String signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
            throw new BadCredentialsException("User with email this email already registered!");
        }

        userService.saveUser(convertToUser(signUpRequest));
        return authenticateAndGetToken(signUpRequest.getEmail(), signUpRequest.getPassword());
    }

    private String authenticateAndGetToken(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return tokenProvider.generateToken(authentication);
    }

    private User convertToUser(SignUpRequest signUpRequest) {
        return User.builder()
                .firstname(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.ADMIN)
                .build();
    }

}
