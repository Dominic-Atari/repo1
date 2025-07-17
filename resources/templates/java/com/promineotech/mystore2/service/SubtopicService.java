package com.promineotech.mystore2.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.mystore2.model.Subtopic;
import com.promineotech.mystore2.repository.SubtopicRepository;

@Service
public class SubtopicService {

	@Autowired
    private SubtopicRepository subtopicRepository;
	
	public List<Subtopic> getAllSubtopics() {
		 return subtopicRepository.findAll();
	}
    
	public Subtopic saveSubtopic(Subtopic subject) {
		return subtopicRepository.save(subject);
	}
	
	public Subtopic getSubTopicById(Long id) {
		return subtopicRepository.findById(id).orElseThrow(() -> new NoSuchElementException("ID with " +id+ "was not found."));
	}
	
    public void createSubtopic(String subtopicName) {
        Subtopic subtopic = new Subtopic();
        subtopic.setSubtopicName(subtopicName);
        subtopicRepository.save(subtopic);
    }


}