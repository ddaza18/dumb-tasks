package com.dumb.tasks.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dumb.tasks.models.User;
import com.dumb.tasks.repositorys.UserRepository;

@Service
public class CustomerDetailService implements UserDetailsService{
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomerDetailService.class);
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findLoginByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario NO encontrado " + username ));
		LOG.info("Validando Usuario: " + user.getUsername());
		UserDetails userDetails = org.springframework.security.core.userdetails.User
				.withUsername(user.getUsername())
				.password(user.getPassword())
				.roles(user.getRoles().stream().map(role -> role.getRol()).toArray(String[]::new))
				.build();
		return userDetails;
	}

}
