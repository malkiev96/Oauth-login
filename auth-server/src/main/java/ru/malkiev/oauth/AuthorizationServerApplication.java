package ru.malkiev.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
public class AuthorizationServerApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
	}

	@RestController
	public static class HelloController {
		@GetMapping("/hello")
		public String hello(Principal principal) {
			return "Hello, " + principal.getName();
		}
	}
}