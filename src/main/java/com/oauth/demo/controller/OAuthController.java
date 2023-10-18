package com.oauth.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class OAuthController {
    @GetMapping
    public String sayHelloWorld() {
        return "Hello world";
    }

    @GetMapping("/secured")
    public String saySecured() {
        return "Secured Hello world";
    }
}