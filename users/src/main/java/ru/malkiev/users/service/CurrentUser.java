package ru.malkiev.users.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import ru.malkiev.users.entity.User;
import ru.malkiev.users.exception.UserNotFoundException;
import ru.malkiev.users.repository.UserRepository;

@Component
@AllArgsConstructor
public class CurrentUser {

  private final UserRepository userRepository;

  public User getUser() {
    Long userId = getUserId();
    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
  }

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
