package com.greedy.mingle.message.dto;

import java.util.Date;

import com.greedy.mingle.employee.dto.EmployeeDTO;

import lombok.Data;

@Data
public class MessageDTO {

	private Long msgCode;
	private String msgContent;
	private Date msgSendDate;
	private String msgImpSender;
	private String msgImpReceiver;
	private String msgReadYn;
	private EmployeeDTO sender;
	private EmployeeDTO receiver;
	private String msgDelSender;
	private String msgDelReceiver;
	
}
