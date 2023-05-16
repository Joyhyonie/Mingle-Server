package com.greedy.mingle.employee.dto;



import com.greedy.mingle.employee.entity.EmployeeRolePk;

import lombok.Data;

@Data
public class EmployeeRoleDTO {

	private EmployeeRolePk employeeRolepk;
	private AuthDTO auth;

}
