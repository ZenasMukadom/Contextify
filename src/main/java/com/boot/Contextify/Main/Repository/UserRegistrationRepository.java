package com.boot.Contextify.Main.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.Contextify.Main.Entity.UserRegistration;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Integer> {

	UserRegistration findByUsername(String username);
	
	
}
