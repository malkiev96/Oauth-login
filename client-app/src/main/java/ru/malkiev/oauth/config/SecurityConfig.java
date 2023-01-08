package ru.malkiev.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http
        .authorizeExchange(authorizeRequests -> authorizeRequests
            .anyExchange().permitAll()
        )
        .oauth2Login(Customizer.withDefaults())
        .logout(Customizer.withDefaults())
        .csrf().disable()
        .build();
  }

}
