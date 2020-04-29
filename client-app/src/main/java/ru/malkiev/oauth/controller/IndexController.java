package ru.malkiev.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class IndexController {

	@ResponseBody
	@GetMapping("/user/me")
	public Principal user(Principal principal){
		return principal;
	}

	@ResponseBody
	@GetMapping("/")
	public String hello(Principal principal){
		return "Hello, " + principal.getName();
	}
}
