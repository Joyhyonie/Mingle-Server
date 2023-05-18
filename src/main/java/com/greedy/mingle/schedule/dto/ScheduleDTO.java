package com.greedy.mingle.schedule.dto;

import java.sql.Date;

import com.greedy.mingle.employee.entity.Employee;

import lombok.Data;

@Data
public class ScheduleDTO {
	
	private Long scheCode;
	private String scheName;
	private Date scheStartDate;
	private Date scheEndDate;
	private String colorCode;
	private String doneYn;
	private Employee employee;
	private String scheType;
	private String scheContent;
	
}
