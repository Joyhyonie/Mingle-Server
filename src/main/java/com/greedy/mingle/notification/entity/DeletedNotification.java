package com.greedy.mingle.notification.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.greedy.mingle.employee.entity.Employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="TBL_NOTI_DELETE")
@SequenceGenerator(name="NOTI_DEL_SEQ_GENERATOR",
				   sequenceName="SEQ_NOTI_DEL_CODE",
				   initialValue=1, allocationSize=1)
public class DeletedNotification {

	@Id
	@Column(name="NOTI_DEL_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NOTI_DEL_SEQ_GENERATOR")
	private Long notiDelCode;
	
	@ManyToOne
	@JoinColumn(name="NOTI_CODE")
	private Notification notification;
	
	@ManyToOne
	@JoinColumn(name="EMP_CODE")
	private Employee employee;
	
}
