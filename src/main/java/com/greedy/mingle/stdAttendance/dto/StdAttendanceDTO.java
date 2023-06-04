package com.greedy.mingle.stdAttendance.dto;



import com.greedy.mingle.cource.entity.Course;

import lombok.Data;

@Data
public class StdAttendanceDTO {


	private Long stdAtdCode;		//출결코드
	private String stdAtdStatus;		//출결상태
	private Long stdAtdDate;		//출결 날짜
	private Course course;		//수강코드
	
	/* 수강코드로 해당 강의를 수강하는 학생들의 출결 조회를 위한 필드 */
	private Long attendanceCount;
	private Long lateCount;
	private Long absenceCount;

}
