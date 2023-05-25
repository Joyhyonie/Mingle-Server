package com.greedy.mingle.stdAttendance.entity;

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

import com.greedy.mingle.cource.entity.Course;
import com.greedy.mingle.subject.entity.Department;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="TBL_STD_ATTENDANCE")
@DynamicInsert
@SequenceGenerator(name="SEQ_STD_ATD_CODE", sequenceName="SEQ_STD_ATD_CODE", allocationSize=1)
public class StdAttendance {
	
	@Id
	@Column(name="STD_ATD_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_STD_ATD_CODE")
	private Long stdCode;				
	
	@Column(name="STD_ATD_STATUS")
	private String stdAtdStatus;
	
	@Column(name="STD_ATD_DATE")
	private String stdAtdDate;
	
	@ManyToOne
	@JoinColumn(name="STD_COURSE_CODE")
	private Course course;
	
	
	
	

	
	/*  UPDATE 하는 무언가 인듯. 

	public void update(String stdNameEn, String stdPwd, String stdName, String stdEmail,
			String stdPhone, String stdAddress, String stdProfile, Date stdEntDate, Date stdAbDate,
			Date stdDropDate, Date stdLeaveDate, String stdStatus, Long stdLevel, String stdSsn, Department department) {
		
		this.stdNameEn = stdNameEn;
		this.stdPwd = stdPwd;
		this.stdName = stdName;
		this.stdEmail = stdEmail;
		this.stdPhone = stdPhone;
		this.stdAddress = stdAddress;
		this.stdProfile = stdProfile;
		this.stdEntDate = stdEntDate;
		this.stdAbDate = stdAbDate;
		this.stdDropDate = stdDropDate;
		this.stdLeaveDate = stdLeaveDate;
		this.stdStatus = stdStatus;
		this.stdLevel = stdLevel;
		this.stdSsn = stdSsn;
		this.department = department;*/
	}
	



