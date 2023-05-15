
package com.greedy.mingle.subject.dto;


import lombok.Data;

@Data
public class SubjectNameDTO {

	/*강의 개설(행정)페이지에 쓰일 DTO*/
private Long sbjCode;
	
	private String sbjName;
	
	private String classType;
	
	private int score;
	
	

}
