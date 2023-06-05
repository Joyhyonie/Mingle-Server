package com.greedy.mingle.employee.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeDTO {
	
	 private String newPassword;
	 private String confirmPassword;
	 private String existingPassword;
		
	 public CharSequence getExistingPassword() {
			
		return existingPassword;
	 }

}
