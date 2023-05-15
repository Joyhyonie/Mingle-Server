package com.greedy.mingle.lecture.dto;

import java.sql.Date;

import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.subject.entity.Department;

import lombok.Data;

@Data
public class LectureOfficerDTO {
	
	private Long lecCode;		//강의코드
	

	private String lecName;//강의명
	

	
	private Date lecYear;// 강의년도
	private Long lecSeason;// 강의 학기
	
	private EmployeeDTO employee;// 강의교수
	private Date lecStartDate;// 강의 시작일
	private Date LecEndDate; //강의 종료일
	private Long lecCount; //총 수업 회차1
	private Department department; //학과
	
	





//	LEC_PER_FINAL	NUMBER	Yes	40	16	
//	LEC_PER_ATD	NUMBER	Yes	10	17	평가기준_출석
//	LEC_PER_TASK	NUMBER	Yes	"10
//	"	18	평가기준_과제

}
