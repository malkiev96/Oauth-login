package ru.malkiev.oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

  @GetMapping("/")
  public String hello() {
    return "Hello";
  }
}