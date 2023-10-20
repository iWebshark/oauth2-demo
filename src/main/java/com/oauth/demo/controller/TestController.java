package com.oauth.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String sayHelloWorld() {
        return "Hello world";
    }

    @GetMapping("/secured")
    public String saySecured() {
        return "Secured Hello world";
    }

    @GetMapping("/secured/admin")
    public String saySecuredAdmin() {
        return "Secured Hello world for Admins";
    }
}
