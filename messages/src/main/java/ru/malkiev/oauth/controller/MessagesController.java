package ru.malkiev.oauth.controller;

import java.security.Principal;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MessagesController {

  @GetMapping("/hello")
  public ResponseEntity<String> test(Principal principal) {
    return ResponseEntity.ok(principal.toString());
  }

}
