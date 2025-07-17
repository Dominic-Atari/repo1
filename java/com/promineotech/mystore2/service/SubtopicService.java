package com.promineotech.mystore2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.mystore2.model.Subtopic;
import com.promineotech.mystore2.repository.SubtopicRepository;

@Service
public class SubtopicService {

	@Autowired
    private SubtopicRepository subtopicRepository;
    
    public void createSubtopic(String subtopicName) {
        Subtopic subtopic = new Subtopic();
        subtopic.setSubtopicName(subtopicName);
        subtopicRepository.save(subtopic);
    }

	public List<Subtopic> getAllSubtopics() {
		 return subtopicRepository.findAll();
	}
}
