package com.promineotech.mystore2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promineotech.mystore2.model.Form1Subjects;
import com.promineotech.mystore2.service.SubjectService;

@Controller
@RequestMapping("/api/subjects")
public class SubjectController {
    
    @Autowired
    private SubjectService subjectService;
    
    
    @GetMapping
    public List<Form1Subjects> getAllSubjects() {
        return subjectService.getAllSubjects();
    }
    
    @GetMapping("/{id}")
    public Form1Subjects getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id);
    }
    
    @PostMapping
    public Form1Subjects saveSubject(@RequestBody Form1Subjects subject) {
        return subjectService.saveSubject(subject);
 
    }
}
