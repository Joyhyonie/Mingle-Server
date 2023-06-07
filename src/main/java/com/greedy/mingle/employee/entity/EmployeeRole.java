package com.greedy.mingle.employee.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
@Entity
@Table(name="TBL_EMP_ROLE")
public class EmployeeRole {

	@EmbeddedId
	private EmployeeRolePk employeeRolePk;
	
	@ManyToOne
	@JoinColumn(name="AUTH_CODE" , insertable = false, updatable = false)
	private Auth auth;

}
