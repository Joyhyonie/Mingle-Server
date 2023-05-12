package com.greedy.mingle.certi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="TBL_CERTI_FORM")
public class CertiForm {
	
	@Id
	@Column(name="CERTI_FORM_CODE")
	private Long certiFormCode;
	
	@Column(name="CERTI_FORM_NAME")
	private String certiFormName;

}
