package com.promineotech.mystore2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "form1_subjects")
public class Form1Subjects {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    
	    private Long form1SubjectId;
	    //@Column(name = "form1_subject")
	    private String form1Subject;
	    // getters and setters
	}