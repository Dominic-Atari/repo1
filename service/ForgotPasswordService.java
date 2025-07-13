package com.promineotech.mystore2.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.promineotech.mystore2.model.Student;
import com.promineotech.mystore2.repository.AppUserRepository;

@Service
public class ForgotPasswordService {
	 @Autowired
	    private AppUserRepository appUserRepository;

	    @Autowired
	    private JavaMailSender javaMailSender;

	    public Student findByEmail(String email) {
	        return appUserRepository.findByEmail(email);
	    }

	    public void saveUser(Student user) {
	        appUserRepository.save(user);
	    }

	    public void sendPasswordResetEmail(String email, String resetLink) {
	        SimpleMailMessage mailMessage = new SimpleMailMessage();
	        mailMessage.setTo(email);
	        mailMessage.setSubject("Password Reset Request");
	        mailMessage.setText("Please click the following link to reset your password: " + resetLink);
	        javaMailSender.send(mailMessage);
	    }

	    public String getLoggedInUserFirstName(Principal principal) {
	        Student user = appUserRepository.findByEmail(principal.getName());
	        return user.getFirstName();
	    }
	}
