package com.greedy.mingle.notification.dto;

import java.util.Date;

import com.greedy.mingle.notification.entity.NotificationType;

import lombok.Data;

@Data
public class NotificationDTO {
	
	private Long notiCode;
	private String notiContent;
	private Date notiStartDate;
	private NotificationTypeDTO notiType;
	private Date notiEndDate;

}
