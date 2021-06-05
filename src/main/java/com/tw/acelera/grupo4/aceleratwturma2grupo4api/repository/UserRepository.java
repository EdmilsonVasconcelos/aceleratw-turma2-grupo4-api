package com.tw.acelera.grupo4.aceleratwturma2grupo4api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);

}
