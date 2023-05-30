package com.greedy.mingle.cource.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.greedy.mingle.lecture.dto.LectureOfficerDTO;
import com.greedy.mingle.lecture.repository.LectureRepository;
import com.greedy.mingle.lecture.service.LectureService;
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
	private final LectureService lectureService;
	
	public CourseController(LectureRepository lectureRepository,ModelMapper modelMapper,DepartmentRepository departmentRepository,EmployeeRepository employeeRepository,SubjectRepository subjectRepository, CourseRepository courseRepository, CourceService courceService, LectureService lectureService) {
		
		this.courseRepository= courseRepository;
		this.lectureRepository =lectureRepository;
		this.departmentRepository = departmentRepository;
		this.modelMapper = modelMapper;
		this.employeeRepository=employeeRepository;
		this.subjectRepository=subjectRepository;
		this.courceService=courceService;
		this.lectureService=lectureService;
	
	}
	
	
	/*1.lecCode를 통해서 해당 학생 리스트 불러오기*/
	@GetMapping("/stdlist/{lecCode}")
	public ResponseEntity<ResponseDTO> selectStudentListByDepartment(
			 @PathVariable Long lecCode){
		log.info("lecCode{}",lecCode);
		
		List<CourseDTO> Coursestdlist = courceService.selectCourceList(lecCode);
		List<LectureOfficerDTO> lectureDTO =lectureService.lectureCount(lecCode);
		
		log.info("Coursestdlist:{}",Coursestdlist);
		Map<String, Object> resultMap = new HashMap<>(); //맵으로 두르면 한번 키값으로 뺴낸다. count정보만 가져와서 , select 박스에 넣을 lecCOunt정보 가져오기 
		resultMap.put("courseStudentList", Coursestdlist);
		resultMap.put("lectureDTO",lectureDTO);
		
		
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", resultMap));
		
	}


}
