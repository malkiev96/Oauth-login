package ru.malkiev.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class DefaultSecurityConfig {

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests()
        .requestMatchers("/signup", "/static/**").permitAll()
        .anyRequest().authenticated()
        .and().headers().frameOptions().sameOrigin()
        .and()
        .formLogin()
        .permitAll()
        .loginPage("/login")
        .loginProcessingUrl("/perform-login")
        .usernameParameter("username")
        .passwordParameter("password")
        .defaultSuccessUrl("/");
    return http.build();
  }

}