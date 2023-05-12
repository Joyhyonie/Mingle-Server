package com.greedy.mingle.employee.dto;

import java.util.Date;

import com.greedy.mingle.subject.dto.DepartmentDTO;

import lombok.Data;

@Data
public class EmployeeDTO {
	
private Long empCode;
	
	private String empName;
	
	private String empNameEn;
	
	private String empEmail;
	
	private String empPhone;
	
	private String empAddress;
	
	private String empProfile;
	
	private Date empEntDate;
	
	private Date empAbDate;
	
	private Date empLeaveDate;
	
	private String empStatus;
	
	private DepartmentDTO department;
	
	private String empPwd;
	
	private String empSsn;
	
	private Long empAnnual;
	
}
