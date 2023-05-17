package com.greedy.mingle.employee.dto;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.greedy.mingle.subject.dto.DepartmentDTO;
import com.greedy.mingle.subject.entity.Department;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class EmployeeDTO implements UserDetails{
	

	
			private String empCode;
			private String empName;
			private String empNameEn;
			private String empEmail;
			private String empPhone;
			private String empAddress;
			private String empProfile;
			private Date empEntDate;
			private Date empAbDate;
			private Date empLeaveDate;
			private String empStatus;
			private String empPwd;
			private String empSsn;
			private Long empAnnual;
			
			private List<EmployeeRoleDTO> empRole;
			private Department department;
		
			private Collection<? extends GrantedAuthority> authorities;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {	
		return authorities;
	}

	public List<EmployeeRoleDTO> getEmpRole() {
	    return empRole;
	}

	public void setEmpRole(List<EmployeeRoleDTO> empRole) {
	    this.empRole = empRole;
	}
	
	@Override
	public String getUsername() {
	
		return empCode;
	}

	@Override
	public String getPassword() {		
		return empPwd;
	}

	@Override
	public boolean isAccountNonExpired() {		
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {		
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {		
		return true;
	}
	@Override
	public boolean isEnabled() {		
		return true;
	}




	





	
	
}