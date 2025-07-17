package com.promineotech.mystore2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping("/teacher")
@PreAuthorize("hasAuthority('TEACHER')") 
public class TeacherController<form1Subjects> {

	@GetMapping("/dashboard")
	public String teacherDashboard(Model model) {
		
		return "teacherDashboard";
	}	 
}