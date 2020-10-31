package com.boot.Contextify.Main.Service;

import java.util.List;

import com.boot.Contextify.Main.Entity.UserRegistration;

public interface UserRegistrationService {
	
	List<UserRegistration> getAllUsers();
	
	public void SaveUserRegistration(UserRegistration userRegist);

	public UserRegistration userfindById(int id);
	
	public int userfindByUsername(String username);
	
	void deleteUserById(int id); 
}
