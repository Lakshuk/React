package com.example.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student.model.Login;

@Repository
public interface LoginRepo extends JpaRepository<Login,Integer>{
	Login findByUsername(String username);

}
