package com.greedy.mingle.employee.service;

import org.modelmapper.ModelMapper;
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
	
	public EmployeeDTO selectInfo(Long empCode) {
		
		log.info("[MemberService] selectInfo start =========================== ");
		log.info("[MemberService] employeeCode : {}", empCode);
		
		Employee employee = employeeRepository.findById(empCode)
				.orElseThrow(() -> new UserNotFoundException(empCode + "를 찾을 수 없습니다."));
		
		log.info("[MemberService] selectInfo start =========================== ");
		return modelMapper.map(employee, EmployeeDTO.class);
	
	}
}
