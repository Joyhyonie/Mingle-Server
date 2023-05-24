package com.greedy.mingle.lecture.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.subject.entity.Subject;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TBL_LECTURE")
@DynamicInsert
@Getter
@Setter
@SequenceGenerator(name="SEQ_LEC_CODE", sequenceName="SEQ_LEC_CODE", allocationSize=1)
public class Lecture {
	
	@Id
	@Column(name="LEC_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_LEC_CODE")
	private Long lecCode;		//강의코드
	
	@Column(name="LEC_SUMMARY")
	private String lecSummary; //강의 개요
	
	@Column(name="LEC_GOAL")
	private String lecGoal; // 강의 목표
	
	@Column(name="LEC_NAME")
	private String lecName;//강의명
	
	@Column(name="LEC_PER_MIDDLE")
	private Long lecPerMiddle; // 평가기준 중간
	
	@Column(name="LEC_METHOD")
	private String lecMethod;// 강의 운영방법
	
	@Column(name="LEC_BOOK_MAIN")
	private String lecBookMain; // 주교재
	
	@Column(name="LEC_BOOK_SUB")
	private String lecBookSub; //부교재
	
	@Column(name="LEC_YEAR")
	private String lecYear;// 강의년도
	
	@Column(name="LEC_SEASON")
	private Long lecSeason;// 강의 학기
	
	@ManyToOne
	@JoinColumn(name="EMP_CODE")
	private Employee employee;// 강의교수 fk
	
	@Column(name="LEC_START_DATE")
	private Date lecStartDate;// 강의 시작일
	
	@Column(name="LEC_END_DATE")
	private Date LecEndDate; //강의 종료일
	
	@Column(name="LEC_COUNT")
	private Long lecCount; //총 수업 회차
	
	@ManyToOne
	@JoinColumn(name="SBJ_CODE")
	private Subject subject; // 과목코드 fk
	
	@Column(name="LEC_PER_FINAL")
	private Long lecPerFinal;// 평가기준_기말
	
	@Column(name="LEC_PER_ATD")
	private Long lecPerAtd;// 평가기준_출석
	
	@Column(name="LEC_PER_TASK")
	private Long lecperTask;//평과기준 _과제 

	












}
