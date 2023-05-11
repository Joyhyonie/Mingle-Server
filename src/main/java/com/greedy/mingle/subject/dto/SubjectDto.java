package com.greedy.mingle.subject.dto;

import com.greedy.mingle.subject.entity.Department;

import lombok.Data;

@Data
public class SubjectDto {

	private Long sbjCode;
	
	private String sbjName;
	
	private String classType;
	
	private int score;
	
	private Department department;
}
