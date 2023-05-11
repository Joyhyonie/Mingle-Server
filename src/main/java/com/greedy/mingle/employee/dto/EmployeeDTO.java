package com.greedy.mingle.employee.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.greedy.mingle.subject.dto.SubjectDTO;
import com.greedy.mingle.subject.entity.Department;

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
	
	private Department deptCode;
	
	private String empPwd;
	
	private String empSsn;
	
	private Long empAnnual;
	
	private SubjectDTO subjectDto;
}
