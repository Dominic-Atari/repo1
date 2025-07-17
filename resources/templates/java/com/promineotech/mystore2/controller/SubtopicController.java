package com.promineotech.mystore2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.promineotech.mystore2.model.Subtopic;
import com.promineotech.mystore2.service.SubtopicService;

@Controller
@RequestMapping("/subtopics")
public class SubtopicController {

	 @Autowired
	    private SubtopicService subtopicService;
		
	    @PostMapping
	    @ResponseBody
	    public Subtopic saveSubtopic(@RequestBody Subtopic subtopic) {
	        return subtopicService.saveSubtopic(subtopic);
	    }
}
