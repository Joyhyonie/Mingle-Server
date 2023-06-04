package com.greedy.mingle.lecture.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greedy.mingle.certi.dto.CertiDocDTO;
import com.greedy.mingle.certi.entity.CertiDoc;
import com.greedy.mingle.employee.dto.EmpoloyeeProfessorNameDTO;
import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.employee.repository.EmployeeRepository;
import com.greedy.mingle.lecture.dto.LectureOfficerDTO;
import com.greedy.mingle.lecture.entity.Lecture;
import com.greedy.mingle.lecture.repository.LectureRepository;
import com.greedy.mingle.message.dto.MessageDTO;
import com.greedy.mingle.message.entity.Message;
import com.greedy.mingle.subject.dto.DeptNameDTO;
import com.greedy.mingle.subject.dto.SubjectNameDTO;
import com.greedy.mingle.subject.entity.Department;
import com.greedy.mingle.subject.entity.Subject;
import com.greedy.mingle.subject.repository.DepartmentRepository;
import com.greedy.mingle.subject.repository.SubjectRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class LectureService {
	private final LectureRepository lectureRepository;
	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	private final SubjectRepository subjectRepository;
	
	public LectureService(LectureRepository lectureRepository,ModelMapper modelMapper,DepartmentRepository departmentRepository,EmployeeRepository employeeRepository,SubjectRepository subjectRepository) {
		
		this.lectureRepository =lectureRepository;
		this.departmentRepository = departmentRepository;
		this.modelMapper = modelMapper;
		this.employeeRepository=employeeRepository;
		this.subjectRepository=subjectRepository;
	}
	
		
	

	/*행정 직원 등록 페이지(학과 목록 조회)*/
	public List<DeptNameDTO> getDeptInfo() {
        Long refDeptCode = (long) 11;

        List<Department> departmentList = departmentRepository.findByRefDeptCode(refDeptCode);
        List<DeptNameDTO> deptNameList = departmentList.stream()
                .map(department -> modelMapper.map(department, DeptNameDTO.class))
                .collect(Collectors.toList());

        return deptNameList;
    }
	/*행정 직원 등록 페이지(교수, 과목 목록 조회)*/
	public Map<String, List<?>> getProfessorsAndLecturesInfo(Long deptCode) {

        List<Employee> professorList = employeeRepository.findByDepartmentDeptCode(deptCode);
        List<EmpoloyeeProfessorNameDTO> professorDTOList = professorList.stream()
                .map(employee -> modelMapper.map(employee, EmpoloyeeProfessorNameDTO.class))
                .collect(Collectors.toList());

        List<Subject> subjectList = subjectRepository.findByDepartmentDeptCode(deptCode);
        List<SubjectNameDTO> subjectNameList = subjectList.stream()
                .map(subject -> modelMapper.map(subject, SubjectNameDTO.class))
                .collect(Collectors.toList());

        Map<String, List<?>> dataMap = Map.of(
                "professorDTOList", professorDTOList,
                "subjectNameList", subjectNameList
        );

        return dataMap;
    }


	/*2. (행정실) 강의배정 하기(신규 강의 등록하기)*/
	@Transactional 
	public void insertLecture(LectureOfficerDTO lectureOfficerDTO) {
		
		log.info("[LectureService lectureOfficerDTO:{}",lectureOfficerDTO);
		
		lectureRepository.save(modelMapper.map(lectureOfficerDTO, Lecture.class));
		
	}

	/*3. 행정직원의 (출석) 강의 리스트 */
	public Page<LectureOfficerDTO> lectureList(int page) {
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("lecCode").descending());
		
		Page<Lecture> lectureList = lectureRepository.findByLecNameNotNull(pageable);
		Page<LectureOfficerDTO> lectureDtoList = lectureList.map(lecture->modelMapper.map(lecture,LectureOfficerDTO.class));
		
		return lectureDtoList;
	}
	
	/*4 행정직원의 (강의개설) 강의 리스트 */
	public Page<LectureOfficerDTO> openLectureList(int page) {
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("lecCode").descending());
		
		Page<Lecture> lectureList = lectureRepository.findAll(pageable);
		Page<LectureOfficerDTO> lectureDtoList = lectureList.map(lecture->modelMapper.map(lecture,LectureOfficerDTO.class));
		
		return lectureDtoList;
	}
	



	
	  public List<LectureOfficerDTO> getMyLectureCerti(Long empCode) {
	  
	  List<Lecture> lecture = lectureRepository.findTop5ByEmployeeEmpCode(empCode);
	  List<LectureOfficerDTO> lectureDTO = lecture.stream().map(myLecture ->
	  modelMapper.map(myLecture, LectureOfficerDTO.class))
	  .collect(Collectors.toList()); return lectureDTO; }
	 
	/*수업 회차 조회를 위한 findBylecCode*/
	public List<LectureOfficerDTO> lectureCount(Long lecCode){
		
		List<Lecture>lecture = lectureRepository.findByLecCode(lecCode);
		List<LectureOfficerDTO> lectureDTO=lecture.stream().map(myLecture -> modelMapper.map(myLecture, LectureOfficerDTO.class))
				.collect(Collectors.toList());
		return lectureDTO;
	}



	public Page<LectureOfficerDTO> getMyLecture(int page, Long empCode) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("lecCode").descending());
		
		Page<Lecture> lecture = lectureRepository.findByEmployeeEmpCode(pageable, empCode);
		Page<LectureOfficerDTO> lectureDtoList = lecture.map(mylecture->modelMapper.map(mylecture,LectureOfficerDTO.class));
		return lectureDtoList;
	}



	public Page<LectureOfficerDTO> getLecNameMyLecture(int page, Long empCode) {
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("lecCode").descending());
		
		Page<Lecture> lecture = lectureRepository.findByEmployeeEmpCodeAndLecNameIsNotNull(pageable, empCode);
		Page<LectureOfficerDTO> lectureDtoList = lecture.map(mylecture->modelMapper.map(mylecture,LectureOfficerDTO.class));
		return lectureDtoList;
	}


	@Transactional
	public void updatePlan(LectureOfficerDTO lectureDTO, Long lecCode) {
		Lecture originMyplan = lectureRepository.findById(lecCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 번호의 직원이 없습니다. EmpCode=" + lectureDTO.getLecCode()));
    
	    originMyplan.setLecName(lectureDTO.getLecName());
	    originMyplan.setLecSummary(lectureDTO.getLecSummary());
	    originMyplan.setLecGoal(lectureDTO.getLecGoal());
	    originMyplan.setLecMethod(lectureDTO.getLecMethod());
	    originMyplan.setLecBookMain(lectureDTO.getLecBookMain());
	    originMyplan.setLecBookSub(lectureDTO.getLecBookSub());

	    // 엔티티가 변경되었으므로, 변경을 저장합니다.
	    lectureRepository.save(originMyplan);
	}
	
	public Page<LectureOfficerDTO> searchLecName(int page, String condition,String name, Long empCode){
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("lecCode").descending());
		if(condition.equals("sbjName")){
			Page<Lecture> findLectureSbjName = lectureRepository.findByEmployeeEmpCodeAndSubjectSbjNameContaining(pageable, empCode, name);
			Page<LectureOfficerDTO> lectureDtoSbjNameList = findLectureSbjName.map(mylec->modelMapper.map(mylec,LectureOfficerDTO.class));
			return lectureDtoSbjNameList;
		} else {		
		Page<Lecture> lecture = lectureRepository.findByEmployeeEmpCodeAndLecNameContaining(pageable, empCode, name);
		Page<LectureOfficerDTO> lectureDtoList = lecture.map(mylecture->modelMapper.map(mylecture,LectureOfficerDTO.class));
		return lectureDtoList;
		}
	}

	/*출석 및 성적관리의 강의리스트를 검색으로 불러오기 */
	public Page<LectureOfficerDTO> selectLectureSearchName(int page, String condition, String name){
	if(condition.equals("empName")) {
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("lecCode").descending());
		Page<Lecture> lectureList = lectureRepository.findByEmployeeEmpName(pageable,name);
		Page<LectureOfficerDTO> lectureOfficerDtoList= lectureList.map(lecture-> modelMapper.map(lecture, LectureOfficerDTO.class));
		return lectureOfficerDtoList;
	}else {
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("lecCode").descending());
		Page<Lecture> lectureList = lectureRepository.findByLecName(pageable, name);
		Page<LectureOfficerDTO> lectureOfficerDTO= lectureList.map(lecture-> modelMapper.map(lecture, LectureOfficerDTO.class));
		return lectureOfficerDTO;
	}
		
		
		
	}


}
