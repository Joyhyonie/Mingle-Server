package com.greedy.mingle.lecture.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class LectureDTO {
	
	private Long lecCode;		//강의코드
	private String lecSummary; //강의 개요
	private String lecGoal; // 강의 목표
	private String lecName;//강의명
	private Long lecPerMiddle; // 평가기준 중간
	private String lecMethod;// 강의 운영방법
	private String lecBookMain; // 주교재
	private String lecBookSub; //부교재
	private Date lecYear;// 강의년도
	private Long lecSeason;// 강의 학기
	private Long EmpCode;// 강의교수
	private Date lecStartDate;// 강의 시작일
	private Date LecEndDate; //강의 종료일
	private Long lecCount; //총 수업 회차
	private Long sbjCode; // 과목코드
	private Long lecPerFinal;// 평가기준_기말
	private Long lecPerAtd;// 평가기준_출석
	private Long lecperTask;//평과기준 _과제




//	LEC_PER_FINAL	NUMBER	Yes	40	16	
//	LEC_PER_ATD	NUMBER	Yes	10	17	평가기준_출석
//	LEC_PER_TASK	NUMBER	Yes	"10
//	"	18	평가기준_과제

}
