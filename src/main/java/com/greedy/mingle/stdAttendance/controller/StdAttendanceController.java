package com.greedy.mingle.stdAttendance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.cource.entity.Course;
import com.greedy.mingle.lecture.entity.Lecture;
import com.greedy.mingle.stdAttendance.service.StdAttendanceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/attendance")
public class StdAttendanceController {
	
	private final StdAttendanceService stdAttendanceService;
	
	public StdAttendanceController(StdAttendanceService stdAttendanceService) {
		this.stdAttendanceService = stdAttendanceService;
	}
	
	
/*lecCode를 통해서      course 클래스의 조회기능이 필요없는게 아닐까? */	
 @GetMapping("/list/{lecCode}")
 public ResponseEntity<ResponseDTO> getattendanceList(@PathVariable("lecCode") Long lecCode,
		 @RequestParam(name="stdAtdDate",defaultValue="1")Long stdAtdDate){
	/* 
	 Course course = new Course();
	 Lecture lecture= new Lecture();
	 lecture.setLecCode(lecCode);
	 course.setLecture(lecture);*/
	 
	 return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", stdAttendanceService.getAttendanceInfo(stdAtdDate, lecCode)));

	 
 }

}
