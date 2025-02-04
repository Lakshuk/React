package com.example.student.controller;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.dto.MailBody;
import com.example.student.dto.StudentDto;
import com.example.student.model.Student;
import com.example.student.repository.ForgotPasswordRepository;
import com.example.student.repository.RegisterRepo;
import com.example.student.repository.StudentRepo;
import com.example.student.model.ForgotPassword;
import com.example.student.model.Register;
import com.example.student.service.EmailService;
import com.example.student.service.StudentService;
import com.example.student.utils.ChangePassword;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins ="http://localhost:5173/")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private RegisterRepo regRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ForgotPasswordRepository forgotRepo;
	
	private final PasswordEncoder passwordEncoder = null;
	
	
	@PostMapping("/save")
	public StudentDto create(@RequestBody StudentDto stu) {
		return studentService.create(stu);
	}
	
	@GetMapping("/get/{id}")
	public StudentDto getById(@PathVariable Integer id) {
		return studentService.getEmpById(id);
	}
	
	@GetMapping("/getAll")
	public List<StudentDto> getAll(){
		return studentService.getAll();
	}
	
	@GetMapping("/getAll1")
	public List<Student> getAll1(){
		return studentService.getAll1();
	}
	
	@PutMapping("/update/{id}")
	public Student updateStudent(@RequestBody StudentDto studentDto, @PathVariable Integer id) {
		return studentService.updateStudent(studentDto, id);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteEmp(@PathVariable Integer id) {
		studentService.deleteStu(id);
	}

	
	// Register user
    @PostMapping("/user/register")
    public String register(@RequestBody Register user) {
        return studentService.registeruser(user);
    }

    // Login user
    @PostMapping("/user/login")
    public String login(@RequestBody Register reg) {
        return studentService.loginUser(reg.getUsername(), reg.getPassword());
    }
    
    
    //send mail for verification
    
    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email){
    	Register reg = regRepo.findByEmail(email);
    	
    	int otp =  optGenerator();
    	
    	MailBody mailBody = MailBody.builder()
    			.to(email)
    			.text("This is the OTP for yourforgot password" + otp)
    			.subject("OTP for forgot password")
    			.build();
    	
    	
    	ForgotPassword fp = ForgotPassword.builder()
    			.otp(otp)
    			.expiryTime(new Date(System.currentTimeMillis() + 70 * 1000))
    			.reg(reg)
    			.build();
    	
    	emailService.sendSimpleMessage(mailBody);
    	forgotRepo.save(fp);
    	
    	return ResponseEntity.ok("Email sent for verification");
    			
    }
    
    
    //getting otp
    
    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp,@PathVariable String email){
    	Register reg = regRepo.findByEmail(email);
    	ForgotPassword fp = forgotRepo.findByOtpAndRegister(otp, reg)
    			.orElseThrow(() -> new RuntimeException("Invalidfor Email:" + email));
    	
    	if(fp.getExpiryTime().before(Date.from(Instant.now()))) {
    		forgotRepo.deleteById(fp.getId());
    		return new ResponseEntity<>("OTP has expiried!", HttpStatus.EXPECTATION_FAILED);
    	}
    	return ResponseEntity.ok("OTP verified!");
    }
    
    
    @PostMapping("/changePassword/{emai}")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword, @PathVariable String email){
    	if(!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
    		return new ResponseEntity<>("Please enter the password again!", HttpStatus.EXPECTATION_FAILED);
    	}
    	String encodepassword = passwordEncoder.encode(changePassword.password());
    	regRepo.updatePassword(email, encodepassword);
    	return ResponseEntity.ok("Password has been changes!");
    	
    }
    
    
    private Integer optGenerator() {
    	Random random = new Random();
    	return random.nextInt(100_000, 999_999);
    }
}
