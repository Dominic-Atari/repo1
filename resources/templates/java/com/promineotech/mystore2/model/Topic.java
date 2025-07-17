package com.promineotech.mystore2.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity

public class Topic {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String topicName;
  
 
  

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getTopicName() {
	return topicName;
}

public void setTopicName(String topicName) {
	this.topicName = topicName;
}



  
  // getters and setters

}
