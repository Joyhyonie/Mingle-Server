package com.greedy.mingle.employee.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.mingle.configuration.exception.UserNotFoundException;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.employee.dto.EmployeeRoleDTO;
import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.employee.repository.EmployeeRepository;
import com.greedy.mingle.subject.entity.Department;
import com.greedy.mingle.subject.repository.DepartmentRepository;
import com.greedy.mingle.util.FileUploadUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;
	private final ModelMapper modelMapper;

	@Value("${image.image-url}")
	private String IMAGE_URL;
	@Value("${image.image-dir}")
	private String IMAGE_DIR;

	public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository,
			ModelMapper modelMapper) {
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
		this.modelMapper = modelMapper;
	}

	/* 1. 교번으로 교직원 목록 조회 - 페이징 */
	public Page<EmployeeDTO> selectEmployeeList(int page) {

		log.info("[EmployeeService] selectEmployeeList start ============================== ");

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("empCode").descending());

		Page<Employee> employeeList = employeeRepository.findAll(pageable);
		Page<EmployeeDTO> employeeDtoList = employeeList.map(employee -> modelMapper.map(employee, EmployeeDTO.class));

		log.info("[EmployeeService] employeeDtoList.getContent() : {}", employeeDtoList.getContent());

		log.info("[EmployeeService] selectEmployeeList end ============================== ");

		return employeeDtoList;

	}

	/* 2. 교직원 목록 조회 - 소속 기준, 페이징 */
	public Page<EmployeeDTO> selectEmployeeListByDepartment(int page, Long deptCode) {

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("empCode").descending());

		/* 전달할 카테고리 엔티티를 먼저 조회한다. */
		Department findDepartment = departmentRepository.findById(deptCode)
				.orElseThrow(() -> new IllegalArgumentException("해당하는 소속/부서가 없습니다. departmentCode = " + deptCode));

		Page<Employee> employeeList = employeeRepository.findByDepartment(pageable, findDepartment);
		Page<EmployeeDTO> employeeDtoList = employeeList.map(employee -> modelMapper.map(employee, EmployeeDTO.class));

		return employeeDtoList;
	}

	/* 3. 교직원 목록 조회 - 교직원명 검색 기준, 페이징 */
	public Page<EmployeeDTO> selectEmployeeListByEmpName(int page, String empName) {

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("empCode").descending());

		Page<Employee> employeeList = employeeRepository.findByEmpName(pageable, empName);
		Page<EmployeeDTO> employeeDtoList = employeeList.map(employee -> modelMapper.map(employee, EmployeeDTO.class));

		return employeeDtoList;
	}

	/* 4. 교직원 상세 조회 - empCode로 교직원 1명 조회 */

	public EmployeeDTO selectEmployee(Long empCode) {

		Employee employee = employeeRepository.findByEmpCode(empCode)
				.orElseThrow(() -> new IllegalArgumentException(empCode + " 에 해당하는 교직원이 없습니다."));

		EmployeeDTO employeeDto = modelMapper.map(employee, EmployeeDTO.class);

		return employeeDto;
	}

	/* 5. 교직원 신규 등록 */
	@Transactional
	public void insertEmployee(EmployeeDTO employeeDto) {

		employeeRepository.save(modelMapper.map(employeeDto, Employee.class));

	}

	/* 6. 교직원 정보 수정 */
	@Transactional
	public void updateEmployee(EmployeeDTO employeeDto) {

		Employee findEmployee = employeeRepository.findById(employeeDto.getEmpCode())
				.orElseThrow(() -> new IllegalArgumentException(employeeDto.getEmpCode() + " 에 해당하는 부서를 조회할 수 없습니다."));

		findEmployee.update(employeeDto.getEmpName(), employeeDto.getEmpNameEn(), employeeDto.getEmpEmail(),
				employeeDto.getEmpPhone(), employeeDto.getEmpAddress(), employeeDto.getEmpProfile(),
				employeeDto.getEmpEntDate(), employeeDto.getEmpAbDate(), employeeDto.getEmpLeaveDate(),
				employeeDto.getEmpStatus(), modelMapper.map(employeeDto.getDepartment(), Department.class),
				employeeDto.getEmpPwd(), employeeDto.getEmpSsn(), employeeDto.getEmpAnnual());
	}

	/*
	 * 7. 교직원 정보 삭제
	 * 
	 * @Transactional public void deleteEmployee(Long empCode) {
	 * 
	 * employeeRepository.deleteById(empCode); }
	 */
	/* 자신의 마이페이지 조회 */

	public EmployeeDTO selectInfo(Long empCode) {

		Employee employee = employeeRepository.findByEmpCode(empCode)
				.orElseThrow(() -> new UserNotFoundException(empCode + "를 찾을 수 없습니다."));

		EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
		employeeDTO.setEmpProfile(IMAGE_URL + employeeDTO.getEmpProfile());

		List<EmployeeRoleDTO> employeeRoleDTOList = employee.getEmpRole().stream()
				.map(role -> modelMapper.map(role, EmployeeRoleDTO.class)).collect(Collectors.toList());

		employeeDTO.setEmpRole(employeeRoleDTOList);

		return employeeDTO;

	}

	/* 8. 조직도 교직원 조회 - 스크롤 */

	/* 9. 조직도 교직원 조회 - 소속 기준 */

	/* 10. 조직도 교직원 조회 - 교직원명 검색 기준 */

	/* 마이페이지 이미지 변경 */
	@Transactional
	public void updateEmp(EmployeeDTO employeeDTO) {
		log.info("[EmployeeService] insertProduct start ============================== ");
		log.info("[EmployeeService] empCode : {}", employeeDTO.getEmpId());

		Employee originMypage = employeeRepository.findById(employeeDTO.getEmpCode())
				.orElseThrow(() -> new IllegalArgumentException("해당 번호의 직원이 없습니다. EmpCode=" + employeeDTO.getEmpCode()));

		log.info("[EmployeeService] getEmpCode : {}", originMypage);

		/* 이미지를 변경하는 경우 */

		/* 이미지를 변경하지 않는 경우에는 별도의 처리가 필요 없음 */

		/* 조회했던 기존 엔티티의 내용을 수정 -> 별도의 수정 메소드를 정의해서 사용하면 다른 방식의 수정을 막을 수 있다. */
		
		originMypage.setEmpName(employeeDTO.getEmpName());
		originMypage.setEmpNameEn(employeeDTO.getEmpNameEn());
		originMypage.setEmpPhone(employeeDTO.getEmpPhone());
		originMypage.setEmpEmail(employeeDTO.getEmpEmail());
		originMypage.setEmpAddress(employeeDTO.getEmpAddress());

		employeeRepository.save(originMypage);

		log.info("[ProductService] insertProduct end ============================== ");
	}



	

}
