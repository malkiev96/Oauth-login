package ru.malkiev.documents.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

  public Long getUserId() {
    Long id = (Long) getJwtAuthenticationToken().getTokenAttributes().get("id");
    if (id != null) {
      return id;
    }
    throw new RuntimeException("User id not found");
  }

  private JwtAuthenticationToken getJwtAuthenticationToken() {
    return (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
  }

}
