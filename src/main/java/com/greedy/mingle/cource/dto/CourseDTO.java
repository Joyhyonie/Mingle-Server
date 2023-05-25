package com.greedy.mingle.cource.dto;





import com.greedy.mingle.lecture.entity.Lecture;
import com.greedy.mingle.student.entity.Student;


import lombok.Data;

@Data
public class CourseDTO {
	
	
	private Long courseCode;	//수강 코드(수강번호) 
			
	
	private Lecture lecture;// 강의교수 fk
			

	private Student student; //학생 fk
	
	
	


	
	
}