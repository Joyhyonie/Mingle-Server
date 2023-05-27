package com.greedy.mingle.cource.controller;



import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.cource.dto.CourseDTO;
import com.greedy.mingle.cource.repository.CourseRepository;
import com.greedy.mingle.cource.service.CourceService;
import com.greedy.mingle.employee.repository.EmployeeRepository;
import com.greedy.mingle.lecture.repository.LectureRepository;
import com.greedy.mingle.subject.repository.DepartmentRepository;
import com.greedy.mingle.subject.repository.SubjectRepository;

import lombok.extern.slf4j.Slf4j;
 
@Slf4j
@RestController
@RequestMapping("/stdattendance")
public class CourseController {
	private final CourceService courceService;
	private final CourseRepository courseRepository;
	private final LectureRepository lectureRepository;
	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	private final SubjectRepository subjectRepository;
	
	public CourseController(LectureRepository lectureRepository,ModelMapper modelMapper,DepartmentRepository departmentRepository,EmployeeRepository employeeRepository,SubjectRepository subjectRepository, CourseRepository courseRepository, CourceService courceService) {
		
		this.courseRepository= courseRepository;
		this.lectureRepository =lectureRepository;
		this.departmentRepository = departmentRepository;
		this.modelMapper = modelMapper;
		this.employeeRepository=employeeRepository;
		this.subjectRepository=subjectRepository;
		this.courceService=courceService;
	
	}
	
	
	/*1.lecCode를 통해서 해당 학생 리스트 불러오기*/
	@GetMapping("/stdlist/{lecCode}")
	public ResponseEntity<ResponseDTO> selectStudentListByDepartment(
			 @PathVariable Long lecCode){
		log.info("lecCode{}",lecCode);
		
		List<CourseDTO> Coursestdlist = courceService.selectCourceList(lecCode);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", Coursestdlist));
		
	}


}
