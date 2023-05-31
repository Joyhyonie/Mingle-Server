package com.greedy.mingle.lecture.dto;

import java.sql.Date;

import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.subject.dto.SubjectDTO;

import lombok.Data;

@Data
public class LectureOfficerDTO {
	
	private Long lecCode;		//강의코드
	private String lecName;//강의명 = 교과목명	
	private String lecYear;// 강의년도  --> db 변환할 필요
	private Long lecSeason;// 강의 학기	
	/*행정직원이 주는 강의 정보 */
	private SubjectDTO subject; 	//과목정보를 가져올 subjectCode를 가져오기 위함
	private EmployeeDTO employee;// 강의교수 empname을 가져오기 위함. 
	private Date lecStartDate;// 강의 시작일
	private Date LecEndDate; //강의 종료일
	private Long lecCount; //총 수업 회차1
	
/*===========================================================*/
	
	private String lecSummary;  // 학습 개요
	private String lecGoal;   //학습목표
	private Long lecPerMiddle;  //중간 평가 기준 
	private String lecMethod;  //평갸 방법
	private String lecBookMain; //메인교재
	private String lecBooksub; //서브 교재
	private Long lecPerFinal; //기말 평가기준
	private Long lecPerAtdNumber;// 평가 기준 출석
	private Long lecPerTask;	//평과 기준 과제

	
	



//	LEC_PER_FINAL	NUMBER	Yes	40	16	평가기준_기말
//	LEC_PER_ATD	NUMBER	Yes	10	17	평가기준_출석
//	LEC_PER_TASK	NUMBER	Yes	"10
//	"	18	평가기준_과제

}
