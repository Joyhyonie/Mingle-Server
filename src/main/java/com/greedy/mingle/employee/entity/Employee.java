package com.greedy.mingle.employee.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.greedy.mingle.subject.entity.Department;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name="TBL_EMPLOYEE")
@DynamicInsert
public class Employee {
	
	
	
	@Id
	@Column(name="EMP_CODE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long empCode;
	
	@Column(name="EMP_ID")
	private String empId;
	
	@Column(name="EMP_NAME")
	private String empName;
	
	@Column(name="EMP_NAME_EN")
	private String empNameEn;
	
	@Column(name="EMP_EMAIL")
	private String empEmail;
	
	@Column(name="EMP_PHONE")
	private String empPhone;
	
	@Column(name="EMP_ADDRESS")
	private String empAddress;
	
	@Column(name="EMP_PROFILE")
	private String empProfile;
	
	@Column(name="EMP_ENT_DATE")
	private Date empEntDate;
	
	@Column(name="EMP_AB_DATE")
	private Date empAbDate;
	
	@Column(name="EMP_LEAVE_DATE")
	private Date empLeaveDate;
	
	@Column(name="EMP_STATUS")
	private String empStatus;
	
	@ManyToOne
	@JoinColumn(name="DEPT_CODE")
	private Department department;
	

	@Column(name="EMP_PWD")
	private String empPwd;
	
	@Column(name="EMP_SSN")
	private String empSsn;
	
	@Column(name="EMP_ANNUAL")
	private Long empAnnual;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="EMP_CODE")
	private List<EmployeeRole> empRole;




	public void update(String empName, String empNameEn, String empEmail, String empPhone, String empAddress, String empProfile, Date empEntDate, 
			Date empAbDate, Date empLeaveDate, String empStatus, Department department, String empPwd, String empSsn, Long empAnnual) {
		this.empName = empName;
		this.empNameEn = empNameEn;
		this.empEmail = empEmail;
		this.empPhone = empPhone;
		this.empAddress = empAddress;
		this.empProfile = empProfile;
		this.empEntDate = empEntDate;
		this.empAbDate = empAbDate;
		this.empLeaveDate = empLeaveDate;
		this.empStatus = empStatus;
		this.department = department;
		this.empPwd = empPwd;
		this.empSsn = empSsn;
		this.empAnnual = empAnnual;

	}










		
		
		
	}
		
		
		
		
		
	

	
	
	


		
	

	

