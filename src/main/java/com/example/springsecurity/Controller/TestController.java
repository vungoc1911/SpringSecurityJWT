package com.example.springsecurity.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "public all";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public String userAccess() {
        return "user content";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public String moderator() {
        return "moderator";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "admin";
    }
}

