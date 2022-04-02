package ru.malkiev.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class DefaultSecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .mvcMatchers("/signup", "/static/**", "/h2-console/**").permitAll()
        .anyRequest().authenticated()
        .and().csrf().ignoringAntMatchers("/h2-console/**")
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