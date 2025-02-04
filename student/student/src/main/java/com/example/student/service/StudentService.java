package com.example.student.service;

import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.student.dto.StudentDto;
import com.example.student.model.Student;
import com.example.student.model.Register;
import com.example.student.repository.StudentRepo;
import com.example.student.repository.LoginRepo;
import com.example.student.repository.RegisterRepo;

import jakarta.mail.MessagingException;

@Service
public class StudentService {

	@Autowired
	private RegisterRepo registerRepo;
	
	@Autowired
	private StudentRepo str;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	public EmailService emailService;
	
	@Autowired
	public LoginRepo loginRepo;
 
	
	public StudentDto create(StudentDto stu) {
//		if(!UtilityClass.numbervalidate(stu.getNumber())) {
//			throw new IllegalArgumentException ("Invalid Mobile Number!");
//		}
		Student student = modelMapper.map(stu, Student.class);
		Student saveStu = str.save(student);
		return modelMapper.map(saveStu, StudentDto.class);
	}
	
	public List<StudentDto> getAll(){
		Student stu = (Student) str.findAll();
		return (List<StudentDto>) modelMapper.map(stu, StudentDto.class);
	}

	public List<Student> getAll1(){
		return str.findAll();
	}
	
	public StudentDto getEmpById(Integer id) {
		Student stu = str.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
		return modelMapper.map(stu, StudentDto.class);
	}
	
	public Student updateStudent(StudentDto studentDto, Integer id) {
		Student emp = str.findById(id).orElseThrow (() -> new RuntimeException ("Employee not found"));
		emp.setName(studentDto.getName());
		emp.setNumber(studentDto.getNumber());
		emp.setEmail(studentDto.getEmail());
//		emp.setPassword(studentDto.getPassword());
//		emp.setCountry(studentDto.getCountry());
		emp.setDepartment(studentDto.getDepartment());
		emp.setSalary(studentDto.getSalary());
		return str.save(emp);
	}
	
	public void deleteStu(Integer id) {
		if(!str.existsById(id)) {
			throw new RuntimeException("Employee" + id + "not found" );
		}
		str.deleteById(id);
	}  
	
	
	
	// Register user
//    public String registerUser(StudentDetails user) throws MessagingException {
//        if (userRepo.findByUsername(user.getUsername()) != null) {
//            return "Username already exists!";
//        }
//        userRepo.save(user);
//        emailService.sendWelcomeEmail(user.getEmail(), user.getPassword());
//        
//        return "User registered successfully!";
//    }
//    
//
//    // Login user
//    public String loginUser(String username, String password) {
//        StudentDetails user = userRepo.findByUsername(username);
//        if (user == null || !user.getPassword().equals(password)) {
//            return "Invalid credentials!";
//        }
//        return "Login successful!";
//    }
	
	
	// register user
		public String registeruser(Register user) {
		    if (registerRepo.findByUsername(user.getUsername()) != null) {
		        return "username already exists!";
		    }
		    //emailService.sendWelcomeEmail(user.getEmail(), user.getPassword());
		    registerRepo.save(user);
		    return "user registered successfully!";
		}


	    // Login user
	    public String loginUser(String username, String password) {
	        Register user = registerRepo.findByUsername(username);
	        if (user == null)  {	
	            return "Invalid credentials!";
	        }
	        if(!user.getPassword().equals(password)){	//comparing password with reg table
	        	return "Invalid credentials!";
	        }
	        return "Login successful!";
	    }


	
}
