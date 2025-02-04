package com.example.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.student.model.ForgotPassword;
import com.example.student.model.Register;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword,Integer>{

	@Query("select fp fromForgotPassword fp where fp.otp=?1 and fp.user=?2")
	Optional<ForgotPassword> findByOtpAndRegister(Integer otp, Register reg);
}
