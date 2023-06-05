package com.greedy.mingle.stdAttendance.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greedy.mingle.employee.dto.EmpoloyeeProfessorNameDTO;
import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.employee.repository.EmployeeRepository;
import com.greedy.mingle.lecture.dto.LectureOfficerDTO;
import com.greedy.mingle.lecture.entity.Lecture;
import com.greedy.mingle.lecture.repository.LectureRepository;
import com.greedy.mingle.stdAttendance.dto.StdAttendanceDTO;
import com.greedy.mingle.stdAttendance.entity.StdAttendance;
import com.greedy.mingle.stdAttendance.repository.StdAttendanceRepository;
import com.greedy.mingle.subject.dto.DeptNameDTO;
import com.greedy.mingle.subject.dto.SubjectNameDTO;
import com.greedy.mingle.subject.entity.Department;
import com.greedy.mingle.subject.entity.Subject;
import com.greedy.mingle.subject.repository.DepartmentRepository;
import com.greedy.mingle.subject.repository.SubjectRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class StdAttendanceService {
	private final StdAttendanceRepository stdAttendanceRepository;
	private final LectureRepository lectureRepository;
	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	private final SubjectRepository subjectRepository;
	
	public StdAttendanceService(LectureRepository lectureRepository,ModelMapper modelMapper,DepartmentRepository departmentRepository,EmployeeRepository employeeRepository,SubjectRepository subjectRepository, StdAttendanceRepository stdAttendanceRepository) {
		
		this.lectureRepository =lectureRepository;
		this.departmentRepository = departmentRepository;
		this.modelMapper = modelMapper;
		this.employeeRepository=employeeRepository;
		this.subjectRepository=subjectRepository;
		this.stdAttendanceRepository=stdAttendanceRepository;
	}
	

	
	public List<StdAttendanceDTO> getAttendanceInfo(Long stdAtdDate, Long lecCode){
	
	List<StdAttendance> stdAttendanceList= stdAttendanceRepository.findByStdAtdDateAndCourseLectureLecCode(stdAtdDate, lecCode);
	List<StdAttendanceDTO> stdAttendanceDtoList =stdAttendanceList.stream().map(attendance->modelMapper.map(attendance,StdAttendanceDTO.class)).collect(Collectors.toList());
	
	return stdAttendanceDtoList;
}


	@Transactional
	public void updateAttendance(StdAttendanceDTO stdAttendanceDto) {
	    StdAttendance findAttendance = stdAttendanceRepository.findById(stdAttendanceDto.getStdAtdCode())
	            .orElseThrow(() -> new IllegalArgumentException("해당 StdAtdCode가 없습니다. getStdAtdCode = " + stdAttendanceDto.getStdAtdCode()));

	    findAttendance.setStdAtdStatus((stdAttendanceDto.getStdAtdStatus()));
	    
	    stdAttendanceRepository.save(findAttendance);
	   
		
	    
	}
	
	
	
}


