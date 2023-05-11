package com.greedy.mingle.attendance.entity;

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

import com.greedy.mingle.employee.entity.Employee;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="TBL_EMP_ATTENDANCE")
@DynamicInsert
public class Attendance {
	
	@Id
	@Column(name="EMP_ATD_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EMP_ATD_CODE")
	@SequenceGenerator(name="SEQ_EMP_ATD_CODE", sequenceName="SEQ_EMP_ATD_CODE", allocationSize=1)
	private Long atdCode;
	
	@Column(name="EMP_ATD_DATE")
	private String atdDate;
	
	@Column(name="EMP_ATD_START_TIME")
	private String atdStartTime;
	
	@Column(name="EMP_ATD_END_TIME")
	private String atdEndTime;
	
	@Column(name="EMP_ATD_STATUS")
	private String atdStatus;
	
	@Column(name="EMP_ATD_ETC")
	private String atdEtc;
	
	@ManyToOne
	@JoinColumn(name="EMP_CODE")
	private Employee employee;
	
	public void update(String atdDate, String atdStartTime, String atdEndTime, String atdStatus, String atdEtc) {
		this.atdDate = atdDate;
		this.atdStartTime = atdStartTime;
		this.atdEndTime = atdEndTime;
		this.atdStatus = atdStatus;
		this.atdEtc = atdEtc;
	}
	
}
