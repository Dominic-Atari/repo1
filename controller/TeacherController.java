package com.promineotech.mystore2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.promineotech.mystore2.model.Form1Subjects;
import com.promineotech.mystore2.model.Teacher;
import com.promineotech.mystore2.repository.SubjectRepository;
import com.promineotech.mystore2.repository.TeacherRepository;

@Controller
//@RequestMapping("/teacher")
@PreAuthorize("hasAuthority('TEACHER')") 
public class TeacherController<form1Subjects> {


	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@GetMapping("/dashboard")
	public String teacherDashboard(Model model) {
		// Add any necessary logic or attributes to the model
		return "teacherDashboard";
	}

	// Add other teacher-related end-points as needed

	@GetMapping("/assign-subject")
	public String assignSubjectForm(Model model) {
		List<Teacher> teachers = teacherRepository.findAll();
		List<Form1Subjects> subjects = subjectRepository.findAll();
		model.addAttribute("teachers", teachers);
		model.addAttribute("subjects", subjects);
		return "assign-subject";
	}

	
	
	 
}