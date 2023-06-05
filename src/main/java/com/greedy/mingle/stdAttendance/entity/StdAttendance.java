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
	private Long stdAtdCode;				
	
	@Column(name="STD_ATD_STATUS")
	private String stdAtdStatus;
	
	@Column(name="STD_ATD_DATE")
	private Long stdAtdDate;
	
	@ManyToOne
	@JoinColumn(name="COURSE_CODE")
	private Course course;
	
		
	
	}
	



