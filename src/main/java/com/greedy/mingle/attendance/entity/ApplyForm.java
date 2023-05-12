package com.greedy.mingle.attendance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TBL_APPLY_FORM")
@Getter
@Setter
public class ApplyForm {

	@Id
	@Column(name="APPLY_FORM_CODE")
	private Long applyFormCode;
	
	@Column(name="APPLY_FORM_NAME")
	private String applyFormName;
}
