package ru.malkiev.files.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceServerConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests().anyRequest().permitAll()
        .and()
        .cors().and().csrf().disable()
        .oauth2ResourceServer()
        .jwt();
    return http.build();
  }

}
