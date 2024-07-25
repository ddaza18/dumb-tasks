package com.dumb.tasks.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

	@SuppressWarnings("deprecation")
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 try {
             http.authorizeRequests(autorizeReq ->
                     autorizeReq.requestMatchers("/dumb/**").permitAll()
                             .anyRequest().authenticated());
		} catch (Exception e) {
			e.getMessage();
		}
     
     return http.build();
	}
    
    @Bean
    InMemoryUserDetailsManager userDetailsManager() {
    	UserDetails userDetails = User.withUsername("user")
    			.password("{noop}password")
    			.roles("USER")
    			.build();
    	return new InMemoryUserDetailsManager(userDetails);
    }
    

}
	