package com.greedy.mingle.schedule.entity;

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

import com.greedy.mingle.employee.entity.Employee;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="TBL_SCHEDULE")
@DynamicInsert
public class Schedule {
	
	@Id
	@Column(name="SCHE_CODE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long scheCode;
	
	@Column(name="SCHE_NAME")
	private String scheName;
	
	@Column(name="SCHE_START_DATE")
	private Date scheStartDate;
	
	@Column(name="SCHE_END_DATE")
	private Date scheEndDate;
	
	@Column(name="COLOR_CODE")
	private String colorCode;
	
	@Column(name="DONE_YN")
	private String doneYn;
	
	@ManyToOne
	@JoinColumn(name="EMP_CODE")
	private Employee employee;
	
	@Column(name="SCHE_TYPE")
	private String scheType;
	
	@Column(name="SCHE_CONTENT")
	private String scheContent;
	
	
	
	/* 나의 일정 수정용 메소드 */
	public void myScheUpdate(String scheName, Date scheStartDate, Date scheEndDate, String colorCode) {
		
		this.scheName = scheName;
		this.scheStartDate = scheStartDate;
		this.scheEndDate = scheEndDate;
		this.colorCode = colorCode;
		
	}
	
	/* 학사 일정 수정용 메소드 */
	public void acScheUpdate(String scheName, Date scheStartDate, Date scheEndDate, String scheContent) {
		
		this.scheName = scheName;
		this.scheStartDate = scheStartDate;
		this.scheEndDate = scheEndDate;
		this.scheContent = scheContent;
		
	}

}
