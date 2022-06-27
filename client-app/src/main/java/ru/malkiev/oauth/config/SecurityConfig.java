package ru.malkiev.oauth.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;

@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http
        .securityMatcher(
            allRequestsBut("/public/**")
        )
        .authorizeExchange(authorizeRequests -> authorizeRequests
            .pathMatchers("/login").permitAll()
            .anyExchange().authenticated()
        )
        .oauth2Login(Customizer.withDefaults())
        .logout(Customizer.withDefaults())
        .csrf().disable()
        .build();
  }

  private static ServerWebExchangeMatcher allRequestsBut(String... paths) {
    List<ServerWebExchangeMatcher> matchers = Arrays.stream(paths)
        .map(PathPatternParserServerWebExchangeMatcher::new)
        .collect(Collectors.toList());
    return new NegatedServerWebExchangeMatcher(new OrServerWebExchangeMatcher(matchers));
  }

}
