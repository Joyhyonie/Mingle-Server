package com.greedy.mingle.subject.dto;

import com.greedy.mingle.subject.entity.Department;
import lombok.Data;

@Data
public class SubjectDTO {

	private Long sbjCode;
	
	private String sbjName;
	
	private String classType;
	
	private int score;
	
	private DepartmentDTO department;

}



