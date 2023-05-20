package com.greedy.mingle.schedule.dto;

import java.util.Date;

import com.greedy.mingle.employee.dto.EmployeeDTO;

import lombok.Data;

@Data
public class ScheduleDTO {
	
	private Long scheCode;
	private String scheName;
	private Date scheStartDate;
	private Date scheEndDate;
	private String colorCode;
	private String doneYn;
	private EmployeeDTO employee;
	private String scheType;
	private String scheContent;
	
}
