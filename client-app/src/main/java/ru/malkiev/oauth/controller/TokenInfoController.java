package ru.malkiev.oauth.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenInfoController {

  @GetMapping("/tokenInfo")
  public Map<String, String> getTokenInfo(
      @AuthenticationPrincipal OAuth2User oAuth2User,
      @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client) {
    return new HashMap<String, String>() {{
      put("user", oAuth2User.getName());
      put("token", client.getAccessToken().getTokenValue());
      put("tokenType", client.getAccessToken().getTokenType().getValue());
    }};
  }

  @GetMapping("/token")
  public OAuth2User getOauth2User(@AuthenticationPrincipal OAuth2User oAuth2User) {
    return oAuth2User;
  }

  @GetMapping("/client")
  public OAuth2AuthorizedClient getClient(
      @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client
  ) {
    return client;
  }

}
