package com.greedy.mingle.employee.dto;

import java.util.Date;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.greedy.mingle.employee.entity.Auth;
import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.employee.entity.EmployeeRolePk;
import com.greedy.mingle.subject.entity.Department;

import lombok.Data;

@Data
public abstract class EmployeeRoleDTO {

	private EmployeeRolePk employeeRolepk;
	
	private Auth authCode;

}
