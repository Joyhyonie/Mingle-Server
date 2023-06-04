package com.greedy.mingle.stdAttendance.repository;


import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.mingle.lecture.entity.Lecture;
import com.greedy.mingle.stdAttendance.entity.StdAttendance;

public interface StdAttendanceRepository extends JpaRepository <StdAttendance, Long> {

						//여기는 필드명 기준 							여기는? 

	List<StdAttendance>findByStdAtdDateAndCourseLectureLecCode(Long stdAtdDate, Long lecCode );

	/* 수강코드로 해당 강의를 수강하는 학생들의 출결 조회 (성적표 조회를 위함) */
	@Query(value="SELECT "
			+ "    SUM(CASE WHEN sa.stdAtdStatus = '출석' THEN 1 ELSE 0 END), "
			+ "    SUM(CASE WHEN sa.stdAtdStatus = '지각' THEN 1 ELSE 0 END), "
			+ "    SUM(CASE WHEN sa.stdAtdStatus = '결석' THEN 1 ELSE 0 END)  "
			+ "FROM StdAttendance sa "
			+ "JOIN sa.course c "
			+ "WHERE c.courseCode = :courseCode ")
	List<Object[]> findStdAttendanceByCourseCode(@Param(value="courseCode")Long courseCode);
  
	
}


	
