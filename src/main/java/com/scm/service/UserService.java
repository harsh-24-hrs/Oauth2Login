package com.scm.service;

import java.util.List;
import java.util.Optional;

import com.scm.entity.User;

public interface UserService {
	Optional<User> getUserById(String id);
	void deleteUserById(String id);
//	Optional<User> updateUser(User user);
	List<User> getAllUsers();
	User save(User user);
	//Optional<User> findByEmail(String email);
}
