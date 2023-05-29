package com.greedy.mingle.stdAttendance.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.mingle.lecture.entity.Lecture;
import com.greedy.mingle.stdAttendance.entity.StdAttendance;

public interface StdAttendanceRepository extends JpaRepository <StdAttendance, Long> {

						//여기는 필드명 기준 							여기는? 
	List<StdAttendance>findByStdAtdDateAndCourseLectureLecCode(Long stdAtdDate, Long lecCode );
	
}


	
