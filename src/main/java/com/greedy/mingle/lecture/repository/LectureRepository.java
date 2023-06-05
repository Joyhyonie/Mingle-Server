package com.greedy.mingle.lecture.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.greedy.mingle.lecture.entity.Lecture;

public interface LectureRepository extends JpaRepository <Lecture, Long> {

	List<Lecture> findTop5ByEmployeeEmpCode(Long empCode);
	
	List<Lecture> findByLecCode(Long lecCode);

	Page<Lecture> findByEmployeeEmpCode(Pageable pageable, Long empCode);

	Page<Lecture> findByEmployeeEmpCodeAndLecNameIsNotNull(Pageable pageable, Long empCode);

	Page<Lecture> findByEmployeeEmpCodeAndLecNameContaining(Pageable pageable, Long empCode, String name);

	Page<Lecture> findByEmployeeEmpCodeAndSubjectSbjNameContaining(Pageable pageable, Long empCode, String name);
	
	Page<Lecture> findByEmployeeEmpNameContainingAndLecNameNotNull(Pageable pageable, String empName);
	
	Page<Lecture> findByLecNameContaining(Pageable pagable,String lecName );

	Page<Lecture> findByLecNameNotNull(Pageable pageable);

	Page<Lecture> findBySubjectSbjNameContaining(Pageable pageable, String name);

	Page<Lecture> findByEmployeeEmpNameContaining(Pageable pageable, String name);
}


	
