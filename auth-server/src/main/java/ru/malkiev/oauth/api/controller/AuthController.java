package ru.malkiev.oauth.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriComponentsBuilder;
import ru.malkiev.oauth.api.dto.UserDto;
import ru.malkiev.oauth.application.service.UserService;

@Controller
@AllArgsConstructor
public class AuthController {

  private final UserService userService;

  @GetMapping("/login")
  public String getLoginPage(HttpServletRequest request, Model model) {
    HttpSession session = request.getSession(false);

    model.addAttribute("errorMessage", getAuthErrorMessage(session));
    model.addAttribute("title", "Вход в систему");
    return "login";
  }

  @GetMapping("/signup")
  public String getSignupPage(Model model) {
    model.addAttribute("title", "Регистрация");
    return "signup";
  }

  @PostMapping("/signup")
  public ResponseEntity<String> performSignup(@Valid UserDto dto) {
    try {
      userService.createUser(dto);
      return ResponseEntity.status(301).location(URI.create("/")).build();
    } catch (IllegalArgumentException e) {
      return getSignupError("exists");
    } catch (Exception e) {
      return getSignupError("unexpected");
    }
  }

  private ResponseEntity<String> getSignupError(String error) {
    return ResponseEntity.status(301)
        .location(UriComponentsBuilder.newInstance()
            .queryParam("error", error)
            .build().toUri()
        ).build();
  }

  private String getAuthErrorMessage(HttpSession session) {
    return Optional.ofNullable(session)
        .map(s -> s.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION))
        .map(ex -> ((AuthenticationException) ex).getMessage())
        .orElse(null);
  }

}
