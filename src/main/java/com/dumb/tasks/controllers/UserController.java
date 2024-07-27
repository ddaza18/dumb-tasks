package com.dumb.tasks.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dumb.tasks.models.User;
import com.dumb.tasks.services.UserService;

@RestController
@RequestMapping("/dumb/users")
@AuthorizeReturnObject
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping
	public List<User> findAll(){
		System.out.println("Ejecutando servicio de listar usuarios -> 200 OK");
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<User> findById (@PathVariable Long id){
		return service.findById(id);
	}
	
	@PostMapping
	public User saveUser(@RequestBody User user) {
		return service.saveUser(user);
	}
	
	@PostMapping("/{id}")
	public User updateOrCreateUser(@PathVariable Long id, @RequestBody User user) {
		Optional<User> userOptional = service.findById(id);
		
		User userActOrNew;
		if(userOptional.isPresent()) {
			userActOrNew = userOptional.get();
			userActOrNew.setLoginUser(user.getLoginUser());
			userActOrNew.setFullName(user.getFullName());
			userActOrNew.setMail(user.getMail());
			userActOrNew.setPassword(user.getPassword());
			userActOrNew.setPhone(user.getPhone());
			userActOrNew.setUpdateDate(new Date());
			userActOrNew.setRoles(user.getRoles());
		}else {
			userActOrNew = new User();
			userActOrNew.setId(id);
			userActOrNew.setLoginUser(user.getLoginUser());
			userActOrNew.setFullName(user.getFullName());
			userActOrNew.setMail(user.getMail());
			userActOrNew.setPassword(user.getPassword());
			userActOrNew.setPhone(user.getPhone());
			userActOrNew.setUpdateDate(new Date());
			userActOrNew.setCreationDate(new Date());
			userActOrNew.setRoles(user.getRoles());
		}
		
		return service.saveUser(userActOrNew);
	}
	
	@DeleteMapping("/{id}")
	public void delteUserById(Long id) {
		service.deleteUserById(id);
	}

}
