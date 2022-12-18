package ru.malkiev.oauth.controller;

import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MessagesController {

  @GetMapping("/hello")
  public ResponseEntity<Map<String, Object>> test() {
    JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext()
        .getAuthentication();
    Map<String, Object> attributes = authentication.getTokenAttributes();

    return ResponseEntity.ok(attributes);
  }

}
