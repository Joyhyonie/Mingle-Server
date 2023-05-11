package com.greedy.mingle.employee.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.greedy.mingle.subject.entity.Department;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="TBL_EMPLOYEE")
public class Employee {
	
	@Id
	@Column(name="EMP_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EMP_CODE")
	@SequenceGenerator(name="SEQ_EMP_CODE", sequenceName="SEQ_EMP_CODE", allocationSize=1)
	private Long empCode;
	
	@Column(name="EMP_NAME")
	private String empName;
	
	@Column(name="EMP_NAME_EN")
	private String empNameEn;
	
	@Column(name="EMP_EMAIL")
	private String empEmail;
	
	@Column(name="EMP_PHONE")
	private String empPhone;
	
	@Column(name="EMP_ADDRESS")
	private String empAddress;
	
	@Column(name="EMP_PROFILE")
	private String empProfile;
	
	@Column(name="EMP_ENT_DATE")
	private Date empEntDate;
	
	@Column(name="EMP_AB_DATE")
	private Date empAbDate;
	
	@Column(name="EMP_LEAVE_DATE")
	private Date empLeaveDate;
	
	@Column(name="EMP_STATUS")
	private String empStatus;
	
	@ManyToOne
	@JoinColumn(name="DEPT_CODE")
	private Department department;
	
	@Column(name="EMP_PWD")
	private String empPwd;
	
	@Column(name="EMP_SSN")
	private String empSsn;
	
	@Column(name="EMP_ANNUAL")
	private Long empAnnual;
	
	/*
	 * EMP_CODE	NUMBER
		EMP_NAME	VARCHAR2(100 BYTE)
		EMP_NAME_EN	VARCHAR2(100 BYTE)
		EMP_EMAIL	VARCHAR2(255 BYTE)
		EMP_PHONE	VARCHAR2(100 BYTE)
		EMP_ADDRESS	VARCHAR2(255 BYTE)
		EMP_PROFILE	VARCHAR2(255 BYTE)
		EMP_ENT_DATE	DATE
		EMP_AB_DATE	DATE
		EMP_LEAVE_DATE	DATE
		EMP_STATUS	VARCHAR2(100 BYTE)
		DEPT_CODE	NUMBER
		EMP_PWD	VARCHAR2(255 BYTE)
		EMP_SSN	VARCHAR2(100 BYTE)
		EMP_ANNUAL	NUMBER */
}