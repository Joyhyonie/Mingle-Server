package com.greedy.mingle.schedule.dto;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime selectedDate;
	
}
