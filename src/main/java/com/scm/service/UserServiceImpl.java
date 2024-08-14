package com.scm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entity.User;
import com.scm.repo.UserRepo;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public Optional<User> getUserById(String id) {
		return userRepo.findById(id);
	}

	@Override
	public void deleteUserById(String id) {
		userRepo.deleteById(id);
	}

//	@Override
//	public Optional<User> updateUser(User user) {
//		if(user==null)
//		return Optional.empty();
//		
//		Optional<User>user2=userRepo.findById(user.getUserId());
//		if(user2.isPresent()) {
//			User temp=user2.get();
//			temp.set
//		}
//	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRoles(List.of("ROLE_USER"));
		return userRepo.save(user);
	}

	
	
	
}
