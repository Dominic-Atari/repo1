package com.promineotech.mystore2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.mystore2.model.Note;
import com.promineotech.mystore2.repository.NoteRepository;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;
    
    public void saveNote(Note note) {
        noteRepository.save(note);
    }

	public List<Note> getNotes() {
		return noteRepository.findAll();
	}
}







