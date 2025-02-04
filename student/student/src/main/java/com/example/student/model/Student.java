package com.example.student.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private String number;
	private String email;
	private String department;
	private Integer Salary;
	
	@OneToOne
	private ForgotPassword forgetPassword;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Integer getSalary() {
		return Salary;
	}
	public void setSalary(Integer salary) {
		Salary = salary;
	}
	
	public ForgotPassword getForgetPassword() {
		return forgetPassword;
	}
	public void setForgetPassword(ForgotPassword forgetPassword) {
		this.forgetPassword = forgetPassword;
	}
	
	
	
	public Student(Integer id, String name, String number, String email, String department, Integer salary,
			ForgotPassword forgetPassword) {
		super();
		this.id = id;
		this.name = name;
		this.number = number;
		this.email = email;
		this.department = department;
		Salary = salary;
		this.forgetPassword = forgetPassword;
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
