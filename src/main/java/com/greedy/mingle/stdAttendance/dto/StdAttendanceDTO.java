package com.greedy.mingle.stdAttendance.dto;



import com.greedy.mingle.cource.entity.Course;

import lombok.Data;

@Data
public class StdAttendanceDTO {


	private Long stdAtdCode;		//출결코드
	
	
	private String stdAtdStatus;		//출결상태
	
	
	private Long stdAtdDate;		//출결 날짜
	

	private Course course;		//수강코드
	

}
