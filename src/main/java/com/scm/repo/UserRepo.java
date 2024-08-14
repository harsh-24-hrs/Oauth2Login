package com.scm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, String>{
	public User findByEmail(String email);
}
