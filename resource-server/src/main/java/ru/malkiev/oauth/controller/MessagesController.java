package ru.malkiev.oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {

  @GetMapping("/articles")
  public String[] getArticles() {
    return new String[]{"Article 1", "Article 2", "Article 3"};
  }

}
