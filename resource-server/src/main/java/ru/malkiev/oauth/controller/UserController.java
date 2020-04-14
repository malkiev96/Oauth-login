package ru.malkiev.oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @GetMapping("/api/username")
    public String username(Principal principal){
        return principal.getName();
    }
}
