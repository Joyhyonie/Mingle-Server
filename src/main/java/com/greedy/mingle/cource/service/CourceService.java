package com.greedy.mingle.cource.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.mingle.cource.dto.CourseDTO;
import com.greedy.mingle.cource.entity.Course;
import com.greedy.mingle.cource.repository.CourseRepository;
import com.greedy.mingle.employee.repository.EmployeeRepository;
import com.greedy.mingle.lecture.entity.Lecture;
import com.greedy.mingle.lecture.repository.LectureRepository;
import com.greedy.mingle.stdAttendance.dto.StdAttendanceDTO;
import com.greedy.mingle.subject.repository.DepartmentRepository;
import com.greedy.mingle.subject.repository.SubjectRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class CourceService {
	private final CourseRepository courseRepository;
	private final LectureRepository lectureRepository;
	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	private final SubjectRepository subjectRepository;
	
	public CourceService(LectureRepository lectureRepository,ModelMapper modelMapper,DepartmentRepository departmentRepository,EmployeeRepository employeeRepository,SubjectRepository subjectRepository, CourseRepository courseRepository) {
		
		this.courseRepository= courseRepository;
		this.lectureRepository =lectureRepository;
		this.departmentRepository = departmentRepository;
		this.modelMapper = modelMapper;
		this.employeeRepository=employeeRepository;
		this.subjectRepository=subjectRepository;
	}
	
	
	/*1.lecCode를 통해서 해당 학생 리스트 불러오기*/
	public List<CourseDTO> selectCourceList(Long lecCode){
		
		/*lecture에서 leccode로 조회해오기*/
		
		Lecture findLecture =lectureRepository.findById(lecCode)
		.orElseThrow(() -> new IllegalArgumentException("해당하는 출석이 없습니다. LecturelecCode = " + lecCode));
		
		List<Course> courseList= courseRepository.findByLecture(findLecture);
		List<CourseDTO> courseDtoList= courseList.stream().map(course->modelMapper.map(course,CourseDTO.class)) .collect(Collectors.toList());
				

		
		return courseDtoList;
		
		
	}
	
	

}
