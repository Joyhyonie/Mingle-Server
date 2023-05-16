package com.greedy.mingle.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
@Entity
@Table(name="TBL_AUTH")
public class Auth {

	@Id
	@Column(name="AUTH_CODE")
	private Long authCode;
	
	@Column(name="AUTH_NAME")
	private String authName;
	
	@Column(name="AUTH_INFO")
	private String authInfo;

}
