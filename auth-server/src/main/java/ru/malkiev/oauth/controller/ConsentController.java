package ru.malkiev.oauth.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class ConsentController {

  private final RegisteredClientRepository clientRepository;
  private final OAuth2AuthorizationConsentService consentService;

  @GetMapping(value = "/oauth2/consent")
  public String consent(Principal principal, Model model,
      @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
      @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
      @RequestParam(OAuth2ParameterNames.STATE) String state) {

    // Remove scopes that were already approved
    Set<String> scopesToApprove = new HashSet<>();
    Set<String> previouslyApprovedScopes = new HashSet<>();
    RegisteredClient registeredClient = this.clientRepository.findByClientId(clientId);
    Assert.notNull(registeredClient, "Client not found");
    OAuth2AuthorizationConsent currentAuthorizationConsent = consentService.findById(
        registeredClient.getId(), principal.getName()
    );
    Set<String> authorizedScopes;
    if (currentAuthorizationConsent != null) {
      authorizedScopes = currentAuthorizationConsent.getScopes();
    } else {
      authorizedScopes = Collections.emptySet();
    }
    for (String requestedScope : StringUtils.delimitedListToStringArray(scope, " ")) {
      if (OidcScopes.OPENID.equals(requestedScope)) {
        continue;
      }
      if (authorizedScopes.contains(requestedScope)) {
        previouslyApprovedScopes.add(requestedScope);
      } else {
        scopesToApprove.add(requestedScope);
      }
    }

    model.addAttribute("title", "Подтвердить");
    model.addAttribute("clientId", clientId);
    model.addAttribute("state", state);
    model.addAttribute("scopes", withDescription(scopesToApprove));
    model.addAttribute("previouslyApprovedScopes", withDescription(previouslyApprovedScopes));

    return "consent";
  }

  private static Set<ScopeWithDescription> withDescription(Set<String> scopes) {
    Set<ScopeWithDescription> scopeWithDescriptions = new HashSet<>();
    for (String scope : scopes) {
      scopeWithDescriptions.add(new ScopeWithDescription(scope));

    }
    return scopeWithDescriptions;
  }

  public static class ScopeWithDescription {

    private static final String DEFAULT_DESCRIPTION = "UNKNOWN SCOPE.";
    private static final Map<String, String> descriptions = new HashMap<>();

    static {
      descriptions.put(OidcScopes.PROFILE, "Это приложение получит доступ к данным вашего профиля");
    }

    public final String scope;
    public final String description;

    ScopeWithDescription(String scope) {
      this.scope = scope;
      this.description = descriptions.getOrDefault(scope, DEFAULT_DESCRIPTION);
    }
  }

}