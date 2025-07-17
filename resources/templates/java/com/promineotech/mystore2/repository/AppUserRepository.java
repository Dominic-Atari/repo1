package com.promineotech.mystore2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.promineotech.mystore2.model.Student;

public interface AppUserRepository extends JpaRepository<Student, Long> {

	public Student findByEmail(String email);
//-------------------------------------------------------------------
	public Student findByPasswordResetToken(String token);

	
	
}
