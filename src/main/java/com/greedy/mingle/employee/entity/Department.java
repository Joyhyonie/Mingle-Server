package com.greedy.mingle.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;

@Getter
@Entity
@Table(name="TBL_DEPARTMENT")
@DynamicInsert
public class Department {
	
	@Column(name="DEPT_CODE")
	private Long deptCode;
	
	@Column(name="DEPT_NAME")
	private String deptName;
	
	@Column(name="REF_DEPT_CODE")
	private String refDeptCode;

	
	/*
	 * DEPT_CODE	NUMBER
		DEPT_NAME	VARCHAR2(100 BYTE)
		REF_DEPT_CODE	VARCHAR2(100 BYTE) 
	*/
}
