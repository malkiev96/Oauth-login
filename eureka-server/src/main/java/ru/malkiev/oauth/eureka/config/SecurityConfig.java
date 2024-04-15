package ru.malkiev.oauth.eureka.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(spec -> spec.anyRequest().authenticated())
        .httpBasic(withDefaults())
        .formLogin(withDefaults())
        .logout(withDefaults())
        .csrf(spec -> spec.ignoringRequestMatchers("/eureka/**"));
    return http.build();
  }

}
