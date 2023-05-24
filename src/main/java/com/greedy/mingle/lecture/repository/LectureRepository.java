package com.greedy.mingle.lecture.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.greedy.mingle.lecture.entity.Lecture;

public interface LectureRepository extends JpaRepository <Lecture, Long> {

///*1. 행정직원 강의 개설 강의 리스트 조회-> 이게 안되면 findbyAll...? */
//	@Query("SELECT l.lecCode, s.classType, s.sbjCode, s.sbjName, s.Score, e.empName, d.deptName " +
//	        "FROM Lecture l " +
//	        "JOIN subject s ON s.sbjCode = l.sbjCode " +
//	        "JOIN employee e ON e.empCode = l.empCode " +
//	        "JOIN department d ON d.deptCode = s.deptCode")
//	Page<Lecture> findLectureList(Pageable pageable);
//	
//	
	

}


	
