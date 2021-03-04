package ru.malkiev.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@EnableResourceServer
public class ResourceServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ResourceServerApplication.class, args);
	}

	@RestController
	public static class HelloController {
		@GetMapping("/hello")
		public String hello(Principal principal) {
			return "Hello, " + principal.getName();
		}
	}

}
