package com.dumb.tasks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.dumb.tasks.models.User;
import com.dumb.tasks.repositorys.UserRepository;

@Controller
@RequestMapping("/home")
public class HomeDumbController {
	
	@Autowired
	private UserRepository repository;

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
	
	
}
