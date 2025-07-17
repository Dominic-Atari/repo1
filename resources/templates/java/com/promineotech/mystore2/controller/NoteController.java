package com.promineotech.mystore2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.promineotech.mystore2.model.Form1Subjects;
import com.promineotech.mystore2.model.Note;
import com.promineotech.mystore2.model.Subtopic;
import com.promineotech.mystore2.model.Teacher;
import com.promineotech.mystore2.model.Topic;
import com.promineotech.mystore2.repository.SubjectRepository;
import com.promineotech.mystore2.repository.SubtopicRepository;
import com.promineotech.mystore2.repository.TeacherRepository;
import com.promineotech.mystore2.repository.TopicRepository;
import com.promineotech.mystore2.service.NoteService;
import com.promineotech.mystore2.service.SubjectService;
import com.promineotech.mystore2.service.SubtopicService;
import com.promineotech.mystore2.service.TeacherService;
import com.promineotech.mystore2.service.TopicService;

@Controller
public class NoteController {
	
	@Autowired
	private SubtopicRepository subtopicRepository;
	
	@Autowired
	private SubtopicService subtopicService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private TeacherService teacherService;
	
	
	/*
	 * @PostMapping("/create-note") public String createNote(@ModelAttribute Note
	 * note) { noteService.saveNote(note); return "redirect:/notes";
	 */
	  
	
	
	//lets make a teacher pre-authorised to only create notes
	
	@GetMapping("/create-note")
	public String createNotePage(Model model) {
	    model.addAttribute("note", new Note());
	    
	    // Get logged-in teacher
	    Teacher loggedInTeacher = getLoggedInTeacher(); // authenticate
	    model.addAttribute("loggedInTeacher", loggedInTeacher);
	    
	    List<Form1Subjects> subjects = subjectRepository.findAll();
	    List<Subtopic> subtopics = subtopicRepository.findAll();
	    List<Topic> topics = topicRepository.findAll();
	    model.addAttribute("subjects", subjects);
	    model.addAttribute("subtopics", subtopics);
	    model.addAttribute("topics", topics);
	    return "createnote";
	}

// this authenticates and display the name of the teacher in create note page.
	private Teacher getLoggedInTeacher() {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"))) {
		        return teacherRepository.findByEmail(auth.getName());
		    } else {
		        throw new IllegalStateException("Logged-in user is not a teacher");
		    }
		}




	@PostMapping("/create-note")
	public String createNote(@ModelAttribute Note note) {
	    noteService.saveNote(note);
	    return "redirect:/notes";
	}
	
	@PostMapping("/create-subject")
    public String createSubject(@RequestParam("form1Subject") String form1Subject) {
        subjectService.createSubject(form1Subject);
        return "redirect:/create-note";
    }
	
	@PostMapping("/create-subtopic")
	public String createSubtopic(@RequestParam("subtopicName") String subtopicName) {
	    subtopicService.createSubtopic(subtopicName);
	    return "redirect:/create-note";
	}
	
	@PostMapping("/create-topic")
	public String createTopic(@RequestParam("topicName") String topicName) {
	    topicService.createTopic(topicName);
	    return "redirect:/create-note";
	}
}
