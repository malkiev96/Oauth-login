package ru.malkiev.oauth.config;

import static java.util.Optional.ofNullable;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import ru.malkiev.oauth.model.CustomUserDetails;
import ru.malkiev.oauth.model.CustomUserDetailsMixin;

@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

  private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
    var authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
    authorizationServerConfigurer
        .authorizationEndpoint(configurer -> configurer.consentPage(CUSTOM_CONSENT_PAGE_URI))
        .oidc(Customizer.withDefaults());
    var endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

    http
        .securityMatcher(endpointsMatcher)
        .authorizeHttpRequests(authorize ->
            authorize.anyRequest().authenticated()
        )
        .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
        .exceptionHandling(exceptions ->
            exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
        )
        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
        .apply(authorizationServerConfigurer);
    return http.build();
  }

  @Bean
  public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate,
      PasswordEncoder passwordEncoder) {
    var repository = new JdbcRegisteredClientRepository(jdbcTemplate);
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
    repository.save(client);
    return repository;
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
      getObjectMapper().addMixIn(CustomUserDetails.class, CustomUserDetailsMixin.class);
    }

  }

}
