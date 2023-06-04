package com.greedy.mingle.student.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.student.dto.StudentDTO;
import com.greedy.mingle.student.entity.Student;
import com.greedy.mingle.student.repository.StudentRepository;
import com.greedy.mingle.subject.entity.Department;
import com.greedy.mingle.subject.repository.DepartmentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentService {

	private final StudentRepository studentRepository;
	private final DepartmentRepository departmentRepository;
	private final ModelMapper modelMapper;

	public StudentService(StudentRepository studentRepository, DepartmentRepository departmentRepository,
			ModelMapper modelMapper) {

		this.studentRepository = studentRepository;
		this.departmentRepository = departmentRepository;
		this.modelMapper = modelMapper;
	}
	
	@Value("${image.image-url}")
	private String IMAGE_URL;
	@Value("${image.image-dir}")
	private String IMAGE_DIR;

	/* 1. 교번으로 교직원 목록 조회 - 페이징 */
	public Page<StudentDTO> selectStudentList(int page) {
		
		log.info("[StudentService] selectStudentList start ============================== ");

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("stdCode").descending());

		Page<Student> studentList = studentRepository.findAll(pageable);
		Page<StudentDTO> studentDtoList = studentList.map(student -> modelMapper.map(student, StudentDTO.class));
		
		/* 클라이언트 측에서 서버에 저장 된 이미지 요청 시 필요한 주소로 가공 */
		studentDtoList.forEach(student -> student.setStdProfile(IMAGE_URL + student.getStdProfile()));
		
		log.info("[StudentService] studentDtoList.getContent() : {}", studentDtoList.getContent());

		log.info("[StudentService] selectStudentList end ============================== ");

		return studentDtoList;

	}

	/* 2. 학생 목록 조회 - 학과 기준, 페이징 */
	public Page<StudentDTO> selectStudentListByDepartment(int page, Long deptCode) {

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("stdCode").descending());

		Department findDepartment = departmentRepository.findById(deptCode)
				.orElseThrow(() -> new IllegalArgumentException("해당하는 학과가 없습니다. departmentCode = " + deptCode));

		Page<Student> studentList = studentRepository.findByDepartment(pageable, findDepartment);
		Page<StudentDTO> studentDtoList = studentList.map(student -> modelMapper.map(student, StudentDTO.class));
		
		/* 클라이언트 측에서 서버에 저장 된 이미지 요청 시 필요한 주소로 가공 */
//		studentDtoList.forEach(student -> student.getStdProfile(IMAGE_URL + student.getStdProfile()));

		return studentDtoList;
	}

	/* 3. 학생 목록 조회 - 학생명 검색 기준, 페이징 */
	public Page<StudentDTO> selectStudentListByDeptName(int page, String condition, String name) {
		if(condition.equals("deptName")) {
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("empCode").descending());
		Page<Student> studentList = studentRepository.findByDepartmentDeptName(pageable, name);		
		Page<StudentDTO> stduentDtoDeptList = studentList.map(student -> modelMapper.map(student, StudentDTO.class));
		return stduentDtoDeptList;
		} else {
			Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("stdCode").descending());
			Page<Student> studentList = studentRepository.findByStdName(pageable, name);
			Page<StudentDTO> studentDtoList = studentList.map(student -> modelMapper.map(student, StudentDTO.class));
			return studentDtoList;
		}
	}

	/* 4. 학생 상세 조회 - stdCode로 학생 1명 조회 */
	public StudentDTO selectStudent(Long stdCode) {

		Student student = studentRepository.findByStdCode(stdCode)
				.orElseThrow(() -> new IllegalArgumentException(stdCode + " 에 해당하는 학생이 없습니다."));

		StudentDTO studentDto = modelMapper.map(student, StudentDTO.class);

		return studentDto;
	}

	/* 5. 학생 신규 등록 */
	@Transactional
	public void insertStudent(StudentDTO studentDto) {

		studentRepository.save(modelMapper.map(studentDto, Student.class));

	}

	/* 6. 학생 정보 수정 */
	@Transactional
	public void updateStudent(StudentDTO studentDto) {

		Student findStudent = studentRepository.findById(studentDto.getStdCode())
				.orElseThrow(() -> new IllegalArgumentException(studentDto.getStdCode() + " 에 해당하는 학과를 조회할 수 없습니다."));
		
		findStudent.update(
				studentDto.getStdNameEn(),
				studentDto.getStdPwd(),
				studentDto.getStdName(),
				studentDto.getStdEmail(),
				studentDto.getStdPhone(),
				studentDto.getStdAddress(),
				studentDto.getStdProfile(),
				studentDto.getStdEntDate(),
				studentDto.getStdAbDate(),
				studentDto.getStdDropDate(),
				studentDto.getStdLeaveDate(),
				studentDto.getStdStatus(),
				studentDto.getStdLevel(),
				studentDto.getStdSsn(),
				modelMapper.map(studentDto.getDepartment(), Department.class)
				);
	}

	/* 7. 학생 정보 삭제 */
	@Transactional
	public void deleteStudent(Long stdCode) {
		
		studentRepository.deleteById(stdCode);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
