package com.greedy.mingle.notification.dto;

import com.greedy.mingle.employee.dto.EmployeeDTO;

import lombok.Data;

@Data
public class DeletedNotificationDTO {
	
	private Long notiDelCode;
	private NotificationDTO notification;
	private EmployeeDTO employee;

}
