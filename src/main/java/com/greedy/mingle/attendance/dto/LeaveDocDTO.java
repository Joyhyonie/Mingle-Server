package com.greedy.mingle.attendance.dto;

import com.greedy.mingle.attendance.entity.ApplyForm;
import com.greedy.mingle.employee.dto.EmployeeDTO;

import lombok.Data;

@Data
public class LeaveDocDTO {

	private Long leaveDocCode;
	
	private String applyDate;
	
	private String signDate;
	
	private String docStatus;
	
	private String reason;
	
	private ApplyForm applyForm;
	
	private String startDate;
	
	private String endDate;
	
	private EmployeeDTO leaveApplyer;
	
	private EmployeeDTO accepter;
}
