package ru.malkiev.oauth.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

	@GetMapping("/securedPage")
	public String index(){
		return "securedPage";
	}

	@ResponseBody
	@GetMapping("/user/me")
	public OAuth2Authentication user(OAuth2Authentication principal){
		return principal;
	}
}
