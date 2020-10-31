package com.boot.Contextify.Main.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.boot.Contextify.Main.Entity.UserRegistration;
import com.boot.Contextify.Main.Repository.UserRegistrationRepository;

@Component
public class MyUserDetails implements UserDetailsService {
	
	@Autowired
	private UserRegistrationRepository userRegistRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserRegistration user = userRegistRepo.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("User 404");
		}

		return new UserPrinciple(user);
	}

}
