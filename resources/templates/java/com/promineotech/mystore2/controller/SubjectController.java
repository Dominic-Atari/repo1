package com.promineotech.mystore2.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.promineotech.mystore2.model.Form1Subjects;
import com.promineotech.mystore2.service.SubjectService;
@Controller
@RequestMapping("/subjects")
public class SubjectController {
	
    @Autowired
    private SubjectService subjectService;
	
    @PostMapping
    @ResponseBody
    public Form1Subjects saveSubject(@RequestBody Form1Subjects subject) {
        return subjectService.saveSubject(subject);
    }
}