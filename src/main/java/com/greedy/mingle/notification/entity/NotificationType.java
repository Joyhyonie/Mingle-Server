package com.greedy.mingle.notification.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="TBL_NOTI_TYPE")
public class NotificationType {

	@Id
	@Column(name="NOTI_TYPE_CODE")
	private Long notiTypeCode; 
	
	@Column(name="NOTI_TITLE")
	private String notiTitle; 
	
}
