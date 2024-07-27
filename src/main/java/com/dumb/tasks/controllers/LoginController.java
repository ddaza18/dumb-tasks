package com.dumb.tasks.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class LoginController {
	
	@GetMapping
	public String getLogin() {
		return "login.html";
	}
	
	@GetMapping("/logout")
	public String getLogut() {
		return "logut.jsp";
	}

}