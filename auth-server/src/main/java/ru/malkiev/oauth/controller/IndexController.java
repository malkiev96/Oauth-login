package ru.malkiev.oauth.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

  @GetMapping("/**")
  public String index(Model model, Principal principal) {
    model.addAttribute("title", "Auth server");
    model.addAttribute("username", principal.getName());
    return "index";
  }

}
