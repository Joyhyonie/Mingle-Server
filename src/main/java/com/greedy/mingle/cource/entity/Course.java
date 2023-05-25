package com.greedy.mingle.cource.entity;

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

import com.greedy.mingle.lecture.entity.Lecture;
import com.greedy.mingle.student.entity.Student;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TBL_COURSE")
@DynamicInsert
@Getter
@Setter
@SequenceGenerator(name="SEQ_COURSE_CODE", sequenceName="SEQ_COURSE_CODE", allocationSize=1)
public class Course {
	
	@Id
	@Column(name="COURSE_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_COURSE_CODE")
	private Long courseCode;		//수강 코드(수강번호) 
			
	@ManyToOne
	@JoinColumn(name="LEC_CODE")
	private Lecture lecture;// 강의교수 fk
			
	@ManyToOne
	@JoinColumn(name="STD_CODE")
	private Student student; //학생 fk
	
	
	












}
