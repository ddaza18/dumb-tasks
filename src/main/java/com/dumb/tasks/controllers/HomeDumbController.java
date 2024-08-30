package com.dumb.tasks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.dumb.tasks.models.User;
import com.dumb.tasks.repositorys.UserRepository;
import com.dumb.tasks.services.UserService;

@Controller
@RequestMapping("/home")
@AuthorizeReturnObject
public class HomeDumbController {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserService userService;

	@GetMapping("/tasks")
	public String getHomeDumb(Model model) {
		String username = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null && auth.isAuthenticated()) {
			username = auth.getName();
		}
		
		User user = repository.findLoginByUsername(username).orElseThrow();
		model.addAttribute("username", username);
		model.addAttribute("role", user.getRoles().iterator().next().getRol());
		
		return "home";
	}
	
	@GetMapping("/userCreate")
	public String pageUserCreate() {
		return "userCreate";
	}
	
	
}
