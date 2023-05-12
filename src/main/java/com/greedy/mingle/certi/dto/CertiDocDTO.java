package com.greedy.mingle.certi.dto;

import com.greedy.mingle.employee.dto.EmployeeDTO;

import lombok.Data;

@Data
public class CertiDocDTO {
	
	private Long certiDocCode;
	
	private String certiApplyDate;
	
	private String signDate;
	
	private String docStatus;
	
	private String reason;
	
	private EmployeeDTO applyer;
	
	private EmployeeDTO accepter;
	
	private CertiFormDTO certiForm;
	
	private String certiUse;

}
