package com.boot.Contextify.Main.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.Contextify.Main.Entity.UserRegistration;
import com.boot.Contextify.Main.Repository.UserRegistrationRepository;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
	

	
	@Autowired
	private UserRegistrationRepository userRegistRepo;

	@Override
	public void SaveUserRegistration(UserRegistration userRegist) {
		
		this.userRegistRepo.save(userRegist);
	}

	@Override
	public UserRegistration userfindById(int id) {
		Optional<UserRegistration> optional = userRegistRepo.findById(id);
		UserRegistration update= null;
		if(optional.isPresent()) {
			update = optional.get();
		}
		else {
			throw new RuntimeException("User not Found");
		}
		return update;
	}

	@Override
	public int userfindByUsername(String username) {
		
		UserRegistration user = userRegistRepo.findByUsername(username);
		int userId = user.getId();
		return userId;
	}

	@Override
	public List<UserRegistration> getAllUsers() {
		
		//List of All Users 
		return userRegistRepo.findAll();
	}

	@Override
	public void deleteUserById(int id) {
		this.userRegistRepo.deleteById(id);
	}

}
