package ru.malkiev.oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MyController {

    @GetMapping("/api/principal")
    public Principal principal(Principal principal){
        return principal;
    }
}
