package com.greedy.mingle.employee.dto;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Data;

@Data
public class AuthDTO {

	
	private Long authCode;
	private String authName;	
	private String authInfo; 

}
