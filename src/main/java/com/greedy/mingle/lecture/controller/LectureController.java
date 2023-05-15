package com.greedy.mingle.lecture.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.lecture.dto.LectureOfficerDTO;
import com.greedy.mingle.lecture.service.LectureService;

@RestController
@RequestMapping("/lecture")
public class LectureController {
	
	private final LectureService lectureService;
	
	public LectureController(LectureService lectureService) {
		this.lectureService=lectureService;
	}
	
	/*행정 직원 등록 페이지(학과 목록 조회)*/
	@GetMapping("departments")
	public ResponseEntity<ResponseDTO> getDeptInfo(){
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", lectureService.getDeptInfo()));

		
	}
	/*행정 직원 등록 페이지(교수, 과목 목록 조회)*/
	@GetMapping("professorsAndLectures")
	public ResponseEntity<ResponseDTO> getProfessorsAndLecturesInfo(Long deptCode){
		
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", lectureService.getProfessorsAndLecturesInfo(deptCode)));

		
	}
	
	/*행정직원 등록3(입력)*/
	@PostMapping("officerregistration")
	public ResponseEntity<ResponseDTO> insertLecture (@ModelAttribute LectureOfficerDTO lectureOfficerDTO){
		
		
		lectureService.insertLecture(lectureOfficerDTO);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "등록 성공"));
		
	}
	
	

}
