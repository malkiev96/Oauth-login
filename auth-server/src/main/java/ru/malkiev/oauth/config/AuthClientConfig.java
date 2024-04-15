package ru.malkiev.oauth.config;

import static java.util.Optional.ofNullable;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

@Slf4j
@Configuration
public class AuthClientConfig {

  /**
   * Только для локального запуска
   */
  @Bean
  public CommandLineRunner initAuthClient(RegisteredClientRepository repository,
      PasswordEncoder passwordEncoder) {
    return args -> {
      var client = ofNullable(repository.findByClientId("auth-client"))
          .orElseGet(() -> RegisteredClient.withId(UUID.randomUUID().toString())
              .clientId("auth-client")
              .clientSecret(passwordEncoder.encode("auth-secret"))
              .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
              .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
              .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
              .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
              .redirectUri("http://127.0.0.1:8080/login/oauth2/code/gateway")
              .redirectUri("http://127.0.0.1:8080/authorized")
              .scope(OidcScopes.OPENID)
              .scope(OidcScopes.PROFILE)
              .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
              .build());
      log.info("Initialized auth client");
      repository.save(client);
    };
  }

}
