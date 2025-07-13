package com.promineotech.mystore2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.promineotech.mystore2.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

	Teacher findByEmail(String email);

	Teacher findByPasswordResetToken(String token);
	
	

}
