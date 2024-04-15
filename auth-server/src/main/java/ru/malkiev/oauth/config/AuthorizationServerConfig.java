package ru.malkiev.oauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import ru.malkiev.oauth.config.jackson.AppUserDetailsMixin;
import ru.malkiev.oauth.domain.AppUserDetails;

@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

  private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
        .authorizationEndpoint(endpoint -> endpoint.consentPage(CUSTOM_CONSENT_PAGE_URI))
        .oidc(Customizer.withDefaults());
    http.exceptionHandling(
            exceptions ->
                exceptions.defaultAuthenticationEntryPointFor(
                    new LoginUrlAuthenticationEntryPoint("/login"),
                    new MediaTypeRequestMatcher(MediaType.TEXT_HTML)))
        .oauth2ResourceServer(c -> c.jwt(Customizer.withDefaults()));

    return http.build();
  }

  @Bean
  public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
    return new JdbcRegisteredClientRepository(jdbcTemplate);
  }

  @Bean
  public OAuth2AuthorizationService authorizationService(
      JdbcTemplate jdbcTemplate, RegisteredClientRepository clientRepository) {
    var service = new JdbcOAuth2AuthorizationService(jdbcTemplate, clientRepository);
    service.setAuthorizationRowMapper(new CustomRowMapper(clientRepository));
    return service;
  }

  @Bean
  public OAuth2AuthorizationConsentService authorizationConsentService(
      JdbcTemplate jdbcTemplate,
      RegisteredClientRepository clientRepository) {
    return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, clientRepository);
  }

  @Bean
  public AuthorizationServerSettings settings(@Value("${project.issuer}") String issuer) {
    return AuthorizationServerSettings.builder()
        .issuer(issuer)
        .build();
  }

  static class CustomRowMapper extends JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper {

    public CustomRowMapper(RegisteredClientRepository clientRepository) {
      super(clientRepository);
      getObjectMapper().addMixIn(AppUserDetails.class, AppUserDetailsMixin.class);
    }

  }

}
