package ru.malkiev.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AuthorizationServerApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(AuthorizationServerApplication.class, args);
  }

}