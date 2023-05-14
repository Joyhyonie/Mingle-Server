package com.greedy.mingle.subject.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.subject.service.DepartmentService;

@RestController
@RequestMapping("/department")

public class DepartmentController {
	
	private final DepartmentService departmentService;
	
	public DepartmentController(DepartmentService departmentService) {
		this.departmentService=departmentService;
	}
	
	/*1. refcode를 통해서 학과 조회하기 */
	@GetMapping("/major")
	public ResponseEntity<ResponseDTO> subjectList(){
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "조회 성공", departmentService.deptNameList()));
	}
		
	
	
	
		
	}
	
	
	
	
	


