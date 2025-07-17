package com.promineotech.mystore2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.mystore2.model.Topic;
import com.promineotech.mystore2.repository.TopicRepository;

@Service
public class TopicService {

	@Autowired
	private TopicRepository topicRepository;
	
	public void createTopic(String topicName) {
		 Topic topic = new Topic();
	        topic.setTopicName(topicName);
	        topicRepository.save(topic);
		
	}

}
