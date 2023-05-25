package com.greedy.mingle.stdAttendance.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.greedy.mingle.lecture.entity.Lecture;
import com.greedy.mingle.stdAttendance.entity.StdAttendance;

public interface StdAttendanceRepository extends JpaRepository <StdAttendance, Long> {


	

}


	
