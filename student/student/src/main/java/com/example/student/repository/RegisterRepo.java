package com.example.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.student.model.Student;

import jakarta.transaction.Transactional;

import com.example.student.model.Register;

@Repository
public interface RegisterRepo extends JpaRepository<Register, Integer> {

	Register findByUsername(String username);
	Register findByEmail(String email);
	
	@Modifying
	@Transactional
	@Query("update Register r set r.password=?2 where r.email=?1")
	void updatePassword(String email, String password);
}
