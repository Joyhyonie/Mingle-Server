package com.greedy.mingle.subject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="TBL_DEPARTMENT")
public class Department {
	
	@Id
	@Column(name="DEPT_CODE")
	private Long deptCode;
	
	@Column(name="DEPT_NAME")
	private String deptName;
	
	@Column(name="REF_DEPT_CODE")
	private String refDeptCode;
	
	
	
}
