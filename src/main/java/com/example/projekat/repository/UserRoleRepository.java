package com.example.projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.projekat.entities.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{

	@Query("select ur from UserRole ur where ur.id = :id")
	UserRole findUserRoleById(@Param("id")int id);
	
	@Query("select ur from UserRole ur where ur.name = :name")
	UserRole findUserRoleByName(@Param("name")String name);
}
