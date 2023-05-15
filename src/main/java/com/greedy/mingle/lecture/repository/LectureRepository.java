package com.greedy.mingle.lecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.mingle.lecture.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
	
	

}
