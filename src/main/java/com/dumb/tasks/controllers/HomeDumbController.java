package com.dumb.tasks.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeDumbController {

	@GetMapping
	public String getHomeDumb() {
		return "home.html";
	}
	
}
