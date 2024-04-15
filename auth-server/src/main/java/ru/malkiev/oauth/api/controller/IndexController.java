package ru.malkiev.oauth.api.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.malkiev.oauth.domain.AppUserDetails;

@Controller
public class IndexController {

  @GetMapping("/")
  public String index(Model model, @AuthenticationPrincipal AppUserDetails details) {
    model.addAttribute("title", "Auth server");
    model.addAttribute("details", details);
    return "index";
  }



}
