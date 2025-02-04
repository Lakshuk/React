package com.example.student.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;

@Entity
@Builder
public class ForgotPassword {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer otp;
	private Date expiryTime;
	
	@OneToOne
	private Register reg;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	public Register getReg() {
		return reg;
	}

	public void setReg(Register reg) {
		this.reg = reg;
	}

	public ForgotPassword(Integer id, Integer otp, Date expiryTime, Register reg) {
		super();
		this.id = id;
		this.otp = otp;
		this.expiryTime = expiryTime;
		this.reg = reg;
	}

	public ForgotPassword() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

}
