package com.greedy.mingle.notification.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="TBL_NOTI")
@DynamicInsert
public class Notification {

	@Id
	@Column(name="NOTI_CODE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notiCode;
	
	@Column(name="NOTI_CONTENT")
	private String notiContent;
	
	@Column(name="NOTI_START_DATE")
	private Date notiStartDate;
	
	@ManyToOne
	@JoinColumn(name="NOTI_TYPE_CODE")
	private NotificationType notiType;
	
	@Column(name="NOTI_END_DATE")
	private Date notiEndDate;

}
