package com.dumb.tasks.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.method.AuthorizationAdvisorProxyFactory;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dumb.tasks.services.CustomerDetailService;

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
				auth.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.requestMatchers("/dumb/**").permitAll()
				.requestMatchers("/auth/**").permitAll()
				.requestMatchers("/home/**").hasAnyRole("ADMIN", "USER").anyRequest().authenticated()); 
		http.formLogin(login -> login
				.loginPage("/auth/login").permitAll()
				.defaultSuccessUrl("/home/tasks",true));
		http.logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/auth/login?logout=true"));
		http.headers(header -> header
				.cacheControl(cacheControl -> cacheControl.disable()));
		
		return http.build();
	}
	
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsManager());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    
    @Bean
     UserDetailsService userDetailsManager() {
    	//Activar unicamente si NO es necesario los usuarios en BD para la autenticación
    	/*PasswordEncoder encoder = passwordEncoder();
    	InMemoryUserDetailsManager managerUserDetails = new InMemoryUserDetailsManager();
    	
    	managerUserDetails.createUser(User.withUsername("user")
    			.password(encoder.encode("123qwe"))
    			.roles("USER")
    			.build());
    	
    	managerUserDetails.createUser(User.withUsername("admin")
    			.password(encoder.encode("123qwe"))
    			.roles("ADMIN")
    			.build());*/
    	
    	return new CustomerDetailService();
    }
    
    @Bean
     Customizer<AuthorizationAdvisorProxyFactory> skipValuesTypes(){
    	return (factory) -> factory.setTargetVisitor(AuthorizationAdvisorProxyFactory.TargetVisitor.defaultsSkipValueTypes()); 
    }
}
	