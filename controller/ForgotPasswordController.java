package com.promineotech.mystore2.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.promineotech.mystore2.model.Student;
import com.promineotech.mystore2.service.ForgotPasswordService;

@Controller
public class ForgotPasswordController {
	
	@Autowired
	private ForgotPasswordService forgotPasswor;
	
	@GetMapping("/reset-password")
    public String getForgotPasswordPage() {
        return "reset-password";
    }
	
	@PostMapping("/reset-password")
	public String processForgotPasswordRequest(@RequestParam("email") String email, Model model) {
	    try {
	        Student user = forgotPasswor.findByEmail(email);
	        if (user != null) {
	            // Generate a password reset token
	            String token = UUID.randomUUID().toString();
	            user.setPasswordResetToken(token);
	            forgotPasswor.saveUser(user);

	            // Send a password reset email to the user
	            String resetLink = "http://localhost:8080/reset-password?token=" + token;
	            forgotPasswor.sendPasswordResetEmail(user.getEmail(), resetLink);

	            model.addAttribute("message", "Password reset email sent successfully!");
	        } else {
	            model.addAttribute("message", "Email address not found.");
	        }
	    } catch (Exception e) {
	        model.addAttribute("message", "An error occurred. Please try again later.");
	    }
	    return "reset-password";
	}
}