package com.greedy.mingle.employee.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.mingle.configuration.exception.UserNotFoundException;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.employee.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeService {
	
	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
		
		
	}
	
	public Page<EmployeeDTO> selectEmployeeList(int page) {
		
		log.info("[EmployeeService] selectEmployeeList start =========================== ");
		log.info("[EmployeeService] selectEmployeeList : {}", page);
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("empCode").descending());
		
		Page<Employee> employeeList = employeeRepository.findAll(pageable);
		Page<EmployeeDTO> employeeDtoList = employeeList.map(employee -> modelMapper.map(employee, EmployeeDTO.class));

		/* 클라이언트 측에서 서버에 저장 된 이미지 요청 시 필요한 주소로 가공 */
//		employeeDtoList.forEach(product -> product.setProductImgUrl(IMAGE_URL + employee.getEmployeeImgUrl()));
		
		log.info("[EmployeeService] employeeDtoList.getContent() : {}", employeeDtoList.getContent());

		
		log.info("[MemberService] selectInfo start =========================== ");
		
		return employeeDtoList;
	
	}

}
