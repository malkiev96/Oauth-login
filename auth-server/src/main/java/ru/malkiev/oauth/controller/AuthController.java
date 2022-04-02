package ru.malkiev.oauth.controller;

import java.net.URI;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriComponentsBuilder;
import ru.malkiev.oauth.dto.UserDto;
import ru.malkiev.oauth.service.UserService;

@Controller
@AllArgsConstructor
public class AuthController {

  private final UserService userService;

  @GetMapping("/login")
  public String getLoginPage(HttpServletRequest request, Model model) {
    HttpSession session = request.getSession(false);

    model.addAttribute("errorMessage", getAuthErrorMessage(session));
    model.addAttribute("redirectUri", getRedirectUri(session));
    model.addAttribute("title", "Вход в систему");
    return "login";
  }

  @GetMapping("/signup")
  public String getSignupPage(Model model) {
    model.addAttribute("title", "Регистрация");
    return "signup";
  }

  @PostMapping("/signup")
  public ResponseEntity<String> performSignup(@Valid UserDto dto, String redirectUri) {
    URI uri = URI.create(redirectUri != null ? redirectUri : "/");
    try {
      userService.createUser(dto);
      return ResponseEntity.status(301).location(uri).build();
    } catch (IllegalArgumentException e) {
      return getSignupError(uri, "exists");
    } catch (Exception e) {
      return getSignupError(uri, "unexpected");
    }
  }

  private ResponseEntity<String> getSignupError(URI redirectUri, String error) {
    return ResponseEntity.status(301)
        .location(UriComponentsBuilder.newInstance()
            .queryParam("redirectUri", redirectUri.toString())
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

  private String getRedirectUri(HttpSession session) {
    return Optional.ofNullable(session)
        .map(s -> (DefaultSavedRequest) s.getAttribute("SPRING_SECURITY_SAVED_REQUEST"))
        .map(sr -> sr.getParameterValues("redirect_uri"))
        .map(r -> r[0])
        .orElse("/");
  }

}
