package com.greedy.mingle.attendance.dto;

import com.greedy.mingle.employee.dto.EmployeeDTO;

import lombok.Data;

@Data
public class AttendanceDTO {
	
	private Long atdCode;
	
	private String atdDate;
	
	private String atdStartTime;
	
	private String atdEndTime;
	
	private String atdStatus;
	
	private String atdEtc;
	
	private EmployeeDTO employee;

}
