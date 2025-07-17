package com.promineotech.mystore2.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.promineotech.mystore2.model.Teacher;
import com.promineotech.mystore2.repository.TeacherRepository;

@Service
public class TeacherService implements UserDetailsService {

	@Autowired
	private TeacherRepository repo;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private PasswordEncoder passwordEncoder;


	// private final PasswordEncoder passwordEncoder;

	@Autowired
	public TeacherService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Teacher teacher = repo.findByEmail(email);
		if (teacher != null) {
			return new User(teacher.getEmail(), teacher.getPassword(),
					List.of(new SimpleGrantedAuthority(teacher.getRole())));
		} else {
			throw new UsernameNotFoundException("Teacher not found with email: " + email);
		}
	}

	public void savePasswordResetToken(String email, String token) {
		Teacher teacher = repo.findByEmail(email);
		teacher.setPasswordResetToken(token);
		repo.save(teacher);
	}

	public void sendPasswordResetEmail(String email, String token) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(email);
		mailMessage.setSubject("Password Reset Request");
		mailMessage.setText(
				"Click the following link to reset your password: http://localhost:8080/reset-password?token=" + token);
		javaMailSender.send(mailMessage);
	}

	public void resetPassword(String token, String password) {
		Teacher teacher = repo.findByPasswordResetToken(token);
		if (teacher != null) {
			teacher.setPassword(passwordEncoder.encode(password));
			teacher.setPasswordResetToken(null);
			repo.save(teacher);
		}
	}

	public String getLoggedInTeacherFirstName(Principal principal) {
		Teacher teacher = getLoggedInTeacher(principal);
		return teacher.getFirstName();
	}

	public Teacher getLoggedInTeacher(Principal principal) {
		return repo.findByEmail(principal.getName());

	}
	public Optional<Teacher> getTeacherByEmail(String email) {
	    return Optional.ofNullable(repo.findByEmail(email));
	}
	
}