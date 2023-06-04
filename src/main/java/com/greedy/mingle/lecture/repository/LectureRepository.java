package com.greedy.mingle.lecture.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.mingle.lecture.entity.Lecture;

public interface LectureRepository extends JpaRepository <Lecture, Long> {

	List<Lecture> findTop5ByEmployeeEmpCode(Long empCode);
	
	List<Lecture> findByLecCode(Long lecCode);

	Page<Lecture> findByEmployeeEmpCode(Pageable pageable, Long empCode);

	Page<Lecture> findByEmployeeEmpCodeAndLecNameIsNotNull(Pageable pageable, Long empCode);

	Page<Lecture> findByEmployeeEmpCodeAndLecNameContaining(Pageable pageable, Long empCode, String name);

	Page<Lecture> findByEmployeeEmpCodeAndSubjectSbjNameContaining(Pageable pageable, Long empCode, String name);


}


	
