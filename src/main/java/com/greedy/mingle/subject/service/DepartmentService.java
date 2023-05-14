package com.greedy.mingle.subject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.mingle.employee.dto.EmpoloyeeProfessorNameDTO;
import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.subject.dto.DeptNameDTO;
import com.greedy.mingle.subject.entity.Department;
import com.greedy.mingle.subject.repository.DepartmentRepository;

@Service
public class DepartmentService {
	private final DepartmentRepository departmentRepository;
	private final ModelMapper modelMapper;
	
	public DepartmentService(ModelMapper modelMapper,DepartmentRepository departmentRepository) {
		
		this.departmentRepository = departmentRepository;
		this.modelMapper = modelMapper;
	}
	
	/*1. 학과정보 조회해오기 */
	
	public List<DeptNameDTO> deptNameList(){
		
		Long refDeptCode=(long)11;
		
		List<Department> professorList = departmentRepository.findByRefDeptCode(refDeptCode);
		List<DeptNameDTO> DeptNameList = professorList.stream()
			    .map(department -> modelMapper.map(department, DeptNameDTO.class))
			    .collect(Collectors.toList());
		
		
		
				


		return DeptNameList;
	}
		
	

}
