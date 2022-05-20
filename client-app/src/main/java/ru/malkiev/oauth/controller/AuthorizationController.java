package ru.malkiev.oauth.controller;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

  @GetMapping("/")
  public String hello(Principal principal) {
    return "Hello, " + principal.getName();
  }
}