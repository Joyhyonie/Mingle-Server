package com.greedy.mingle.cource.service;

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

import com.greedy.mingle.cource.repository.CourseRepository;
import com.greedy.mingle.employee.dto.EmpoloyeeProfessorNameDTO;
import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.employee.repository.EmployeeRepository;
import com.greedy.mingle.lecture.dto.LectureOfficerDTO;
import com.greedy.mingle.lecture.entity.Lecture;
import com.greedy.mingle.lecture.repository.LectureRepository;
import com.greedy.mingle.subject.dto.DeptNameDTO;
import com.greedy.mingle.subject.dto.SubjectNameDTO;
import com.greedy.mingle.subject.entity.Department;
import com.greedy.mingle.subject.entity.Subject;
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
	
	
	/*해당 강의의 출석 정보 조회(?)-> 아직 모르겠음. 일단 데이터 불러오기 */
	
	
//////////////////////////////////////////////////
	

//	/*행정 직원 등록 페이지(학과 목록 조회)*/
//	public List<DeptNameDTO> getDeptInfo() {
//        Long refDeptCode = (long) 11;
//
//        List<Department> departmentList = departmentRepository.findByRefDeptCode(refDeptCode);
//        List<DeptNameDTO> deptNameList = departmentList.stream()
//                .map(department -> modelMapper.map(department, DeptNameDTO.class))
//                .collect(Collectors.toList());
//
//        return deptNameList;
//    }
//	/*행정 직원 등록 페이지(교수, 과목 목록 조회)*/
//	public Map<String, List<?>> getProfessorsAndLecturesInfo(Long deptCode) {
//
//        List<Employee> professorList = employeeRepository.findByDepartmentDeptCode(deptCode);
//        List<EmpoloyeeProfessorNameDTO> professorDTOList = professorList.stream()
//                .map(employee -> modelMapper.map(employee, EmpoloyeeProfessorNameDTO.class))
//                .collect(Collectors.toList());
//
//        List<Subject> subjectList = subjectRepository.findByDepartmentDeptCode(deptCode);
//        List<SubjectNameDTO> subjectNameList = subjectList.stream()
//                .map(subject -> modelMapper.map(subject, SubjectNameDTO.class))
//                .collect(Collectors.toList());
//
//        Map<String, List<?>> dataMap = Map.of(
//                "professorDTOList", professorDTOList,
//                "subjectNameList", subjectNameList
//        );
//
//        return dataMap;
//    }
//
//
//	/*2. (행정실) 강의배정 하기(신규 강의 등록하기)*/
//	@Transactional 
//	public void insertLecture(LectureOfficerDTO lectureOfficerDTO) {
//		
//		log.info("[LectureService lectureOfficerDTO:{}",lectureOfficerDTO);
//		
//		lectureRepository.save(modelMapper.map(lectureOfficerDTO, Lecture.class));
//		
//	}
//
//	/*3. 행정직원의 강의개설 강의 리스트 */
//	public Page<LectureOfficerDTO> lectureList(int page) {
//		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("lecCode").descending());
//		
//		Page<Lecture> lectureList = lectureRepository.findAll(pageable);
//		Page<LectureOfficerDTO> lectureDtoList = lectureList.map(lecture->modelMapper.map(lecture,LectureOfficerDTO.class));
//		
//		return lectureDtoList;
//	} 
	

}
