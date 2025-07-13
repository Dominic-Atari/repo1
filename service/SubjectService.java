package com.promineotech.mystore2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.mystore2.model.Form1Subjects;
import com.promineotech.mystore2.repository.SubjectRepository;

@Service
public class SubjectService {
	    
	    @Autowired
	    private SubjectRepository subjectRepository;
	    
	    public List<Form1Subjects> getAllSubjects() {
	        return subjectRepository.findAll();
	    }
	    
	    public Form1Subjects getSubjectById(Long id) {
	        return subjectRepository.findById(id).orElseThrow();
	    }
	    
	    public Form1Subjects saveSubject(Form1Subjects subject) {
	        return subjectRepository.save(subject);
	    
	}
}
