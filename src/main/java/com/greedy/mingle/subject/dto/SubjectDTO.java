package com.greedy.mingle.subject.dto;

import lombok.Data;

@Data
public class SubjectDTO {

	private Long sbjCode;
	
	private String sbjName;
	
	private String classType;
	
	private int score;
	
	private DepartmentDTO department;
}
