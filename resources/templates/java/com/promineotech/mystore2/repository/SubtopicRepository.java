package com.promineotech.mystore2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.promineotech.mystore2.model.Subtopic;

@Repository
public interface SubtopicRepository extends JpaRepository<Subtopic, Long> {

}
