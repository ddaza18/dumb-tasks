package com.dumb.tasks.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dumb.tasks.models.User;
import com.dumb.tasks.repositorys.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public List<User> findAll(){
		return repository.findAll();
	}
	
	public Optional<User> findById(Long id) {
		return repository.findById(id);
	}
	
	public User saveUser(User user) {
		return repository.save(user);
	}
	
	public void deleteUserById(Long id) {
		repository.deleteById(id);
	}

}
