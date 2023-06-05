package com.greedy.mingle.cource.dto;

import com.greedy.mingle.lecture.dto.LectureOfficerDTO;
import com.greedy.mingle.student.dto.StudentDTO;

import lombok.Data;

@Data
public class CourseDTO {
	
	private Long courseCode;	//수강 코드(수강번호) 
	private LectureOfficerDTO lecture;// 강의교수 fk
	private StudentDTO student; //학생 fk

}