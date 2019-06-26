package com.li.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.li.test.entities.UserLogin;

public interface UserLoginRepository extends JpaRepository<UserLogin, String>{

	Optional<UserLogin> findById(String accountName);

}
