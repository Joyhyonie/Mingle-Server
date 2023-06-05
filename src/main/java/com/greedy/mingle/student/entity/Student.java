package com.greedy.mingle.student.entity;

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

import com.greedy.mingle.subject.entity.Department;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="TBL_STUDENT")
@DynamicInsert
public class Student {
	
	@Id
	@Column(name="STD_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_STD_CODE")
	@SequenceGenerator(name="SEQ_STD_CODE", sequenceName="SEQ_STD_CODE", allocationSize=1)
	private Long stdCode;
	
	@Column(name="STD_NAME_EN")
	private String stdNameEn;
	
	@Column(name="STD_PWD")
	private String stdPwd;
	
	@Column(name="STD_NAME")
	private String stdName;
	
	@Column(name="STD_EMAIL")
	private String stdEmail;
	
	@Column(name="STD_PHONE")
	private String stdPhone;
	
	@Column(name="STD_ADDRESS")
	private String stdAddress;
	
	@Column(name="STD_PROFILE")
	private String stdProfile;
	
	@Column(name="STD_ENT_DATE")
	private Date stdEntDate;
	
	@Column(name="STD_AB_DATE")
	private Date stdAbDate;
	
	@Column(name="STD_DROP_DATE")
	private Date stdDropDate;
	
	@Column(name="STD_LEAVE_DATE")
	private Date stdLeaveDate;
	
	@Column(name="STD_STATUS")
	private String stdStatus;
	
	@Column(name="STD_LEVEL")
	private Long stdLevel;
	
	@Column(name="STD_SSN")
	private String stdSsn;
	
	@ManyToOne
	@JoinColumn(name="DEPT_CODE")
	private Department department;

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
		this.department = department;
	}
	

}
