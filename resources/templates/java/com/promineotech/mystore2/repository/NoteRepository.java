package com.promineotech.mystore2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.promineotech.mystore2.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
