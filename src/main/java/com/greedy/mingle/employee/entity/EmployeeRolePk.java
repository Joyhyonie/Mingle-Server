package com.greedy.mingle.employee.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.ToString;
@ToString
@Embeddable
public class EmployeeRolePk implements Serializable {
	
	@Column(name = "EMP_CODE")
	private Long empCode;
	 
	@Column(name = "AUTH_CODE")
	private Long authCode;
	 
}
