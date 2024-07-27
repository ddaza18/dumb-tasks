package com.dumb.tasks.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dumb.tasks.models.User;
import com.dumb.tasks.repositorys.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public List<User> findAll(){
		return repository.findAll();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public Optional<User> findById(Long id) {
		return repository.findById(id);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public User saveUser(User user) {
		return repository.save(user);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	public void deleteUserById(Long id) {
		repository.deleteById(id);
	}

}
