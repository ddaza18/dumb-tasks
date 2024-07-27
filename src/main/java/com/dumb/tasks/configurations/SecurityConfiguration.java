package com.dumb.tasks.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.method.AuthorizationAdvisorProxyFactory;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

	@SuppressWarnings("deprecation")
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf( csrf -> csrf.disable())
				.httpBasic(Customizer.withDefaults())
				.authorizeRequests(auth -> 
				auth.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()); 
		http.formLogin(login -> login
				.loginPage("/auth").permitAll()
				.defaultSuccessUrl("/home").permitAll()
				.failureUrl("/auth?error=true").permitAll());
		http.logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/auth?logout=true"));
		
		return http.build();
	}
	
	@Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsManager());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
    
    @Bean
     UserDetailsService userDetailsManager() {
    	PasswordEncoder encoder = passwordEncoder();
    	InMemoryUserDetailsManager managerUserDetails = new InMemoryUserDetailsManager();
    	
    	managerUserDetails.createUser(User.withUsername("user")
    			.password(encoder.encode("123qwe"))
    			.roles("USER")
    			.build());
    	
    	managerUserDetails.createUser(User.withUsername("admin")
    			.password(encoder.encode("123qwe"))
    			.roles("ADMIN")
    			.build());
    	
    	return managerUserDetails;
    }
    
    @Bean
     Customizer<AuthorizationAdvisorProxyFactory> skipValuesTypes(){
    	return (factory) -> factory.setTargetVisitor(AuthorizationAdvisorProxyFactory.TargetVisitor.defaultsSkipValueTypes()); 
    }
    
    

}
	