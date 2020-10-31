package com.boot.Contextify.Main.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetails;
	


	@Bean
	public AuthenticationProvider authProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetails);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());  
		return provider;
	
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		 http
		 	.csrf().disable()
		 	.authorizeRequests()
		 	.antMatchers("/login1/**").authenticated()
	    	.antMatchers("/login1/AdminPage/**").hasAnyAuthority("Admin")
	    	.antMatchers("/login1/EditorPage/**").hasAnyAuthority("Editor")
	    	.and()
	    	.formLogin()
	    	.loginPage("/login1").permitAll()
	    	.failureUrl("/login/Test")
	    	.defaultSuccessUrl("/login1/default")
	    	.and()
	    	.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
		 
		
		 
	}
	
	
	
	
}
