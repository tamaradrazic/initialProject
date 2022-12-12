package com.example.projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.projekat.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	
	@Query("select u from UserEntity u where u.username=:username")
	UserEntity findUserEntityByName(String username);
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
}
