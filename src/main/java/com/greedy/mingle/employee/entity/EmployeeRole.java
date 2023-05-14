package com.greedy.mingle.employee.entity;

import java.io.Serializable;


import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Embeddable
@Entity
@Table(name="EMP_ROLE")
public class EmployeeRole implements Serializable {

	@EmbeddedId
	private EmployeeRolePk employeeRolePk;
	
	@ManyToOne
	@JoinColumn(name="AUTH_CODE" , insertable = false, updatable = false)
	private Auth authCode;

	
}
