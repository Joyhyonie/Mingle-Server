package com.greedy.mingle.employee.dto;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greedy.mingle.subject.dto.DepartmentDTO;
import com.greedy.mingle.subject.entity.Department;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class EmployeeDTO implements UserDetails{
	

	
			private String empCode;
			private String empName;
			private String empNameEn;
			private String empEmail;
			private String empPhone;
			private String empAddress;
			private String empProfile;
			@DateTimeFormat(pattern="yyyy-MM-dd")
			private Date empEntDate;
			@DateTimeFormat(pattern="yyyy-MM-dd")
			private Date empAbDate;
			@DateTimeFormat(pattern="yyyy-MM-dd")
			private Date empLeaveDate;
			private String empStatus;
			private String empPwd;
			private String empSsn;
			private Long empAnnual;
			
			private List<EmployeeRoleDTO> empRole;
			private DepartmentDTO department;
			
			/* DB 컬럼으로 존재하지는 않지만(entity의 필드로 선언하지 않는다) 
			 * 클라이언트에서 넘겨주는 상품 이미지 파일을 저장할 수 있는 필드 선언 */
			@JsonIgnore
			private MultipartFile employeeImage;

		
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