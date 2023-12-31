package com.greedy.mingle.subject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="TBL_SUBJECT")
@Getter
@Setter
public class Subject {
	
	@Id
	@Column(name="SBJ_CODE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sbjCode;
	
	@Column(name="SBJ_NAME")
	private String sbjName;
	
	@Column(name="CLASS_TYPE")
	private String classType;
	
	@Column(name="SCORE")
	private int score;
	
	@ManyToOne
	@JoinColumn(name="DEPT_CODE")
	private Department department;

	public void update(String sbjName, String classType, int score, Department department) {
		this.sbjName = sbjName;
		this.classType = classType;
		this.score = score;
		this.department = department;
	}
	
	

}
