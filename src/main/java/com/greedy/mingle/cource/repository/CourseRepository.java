package com.greedy.mingle.cource.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.mingle.cource.entity.Course;
import com.greedy.mingle.lecture.entity.Lecture;



public interface CourseRepository extends JpaRepository <Course, Long> {


	/*1.lecCode를 통해서 조회하기*/
	List<Course> findByLecture(Lecture lecture);

}


	
