package com.greedy.mingle.lecture.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.greedy.mingle.lecture.entity.Lecture;

public interface LectureRepository extends JpaRepository <Lecture, Long> {

	List<Lecture> findTop5ByEmployeeEmpCode(Long empCode);


	

}


	
