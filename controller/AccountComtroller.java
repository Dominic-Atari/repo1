package com.promineotech.mystore2.controller;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.promineotech.mystore2.model.Student;
import com.promineotech.mystore2.model.RegisterDto;
import com.promineotech.mystore2.model.Teacher;
import com.promineotech.mystore2.repository.AppUserRepository;
import com.promineotech.mystore2.repository.TeacherRepository;

import jakarta.validation.Valid;

@Controller
public class AccountComtroller {

	@Autowired
	private AppUserRepository repo;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	
	@GetMapping("/register")
	public String register(Model model) {
		RegisterDto registerDto = new RegisterDto();
		model.addAttribute(registerDto);
		model.addAttribute("success", false);
		return "register";
	}
	
	@PostMapping("/register")
	public String register(Model model, @Valid @ModelAttribute RegisterDto registerDto, BindingResult result) {
	    // ...

	    try {
	        //create new account
	        var bCryptEncoder = new BCryptPasswordEncoder();
	        if (registerDto.getRole().equals("TEACHER")) {
	            Teacher teacher = new Teacher();
	            teacher.setFirstName(registerDto.getFirstName());
	            teacher.setLastName(registerDto.getLastName());
	            teacher.setEmail(registerDto.getEmail());
	            teacher.setPhone(registerDto.getPhone());
	            teacher.setAddress(registerDto.getAddress());
	            teacher.setRole(registerDto.getRole());
	            teacher.setCreatedAt(LocalDateTime.now());
	            teacher.setPassword(bCryptEncoder.encode(registerDto.getPassword()));
	            teacherRepository.save(teacher);
	        } else {
	            Student newUser = new Student();
	            newUser.setFirstName(registerDto.getFirstName());
	            newUser.setLastName(registerDto.getLastName());
	            newUser.setEmail(registerDto.getEmail());
	            newUser.setPhone(registerDto.getPhone());
	            newUser.setAddress(registerDto.getAddress());
	            newUser.setRole(registerDto.getRole());
	            newUser.setCreatedAt(new Date());
	            newUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));
	            repo.save(newUser);
	        }

	        model.addAttribute("registerDto", new RegisterDto());
	        model.addAttribute("success", true);
	    } catch(Exception ex) {
	        result.addError(new FieldError("registerDto", "firstName", ex.getMessage()));
	    }
	    return "register";
	}
}