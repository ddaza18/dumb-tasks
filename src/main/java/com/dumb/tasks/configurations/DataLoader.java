package com.dumb.tasks.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dumb.tasks.repositorys.UserRepository;

@Configuration
public class DataLoader {
	
    private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);
	
	@Bean
	CommandLineRunner initDataApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			userRepository.findAll().forEach(userRes -> {
				if(!isPasswordEncoded(userRes.getPassword())) {
					String encodePassword = passwordEncoder.encode(userRes.getPassword());
					userRes.setPassword(encodePassword);
					LOG.info("Cargando DATA y encriptando passwords.");
					userRepository.save(userRes);
				}else {
					LOG.info("El password ya ha sido encriptado para el usuario " + userRes.getUsername());
					return;
				}
				
			});
		};		
	}
	
	/**
	 * Valida si la contraseña tiene prefijos de encriptacion en BD
	 * @param password
	 * @return
	 */
	private boolean isPasswordEncoded(String password) {
        return password != null && (password.startsWith("$2a$") || password.startsWith("$2y$"));
    }

}
