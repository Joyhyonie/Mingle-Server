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
	
	// 읽지 않은 쪽지의 갯수를 저장하기 위한 필드
	private int unreadMsgs;
	
	// '13. 선택한 쪽지 삭제'를 위한 필드
	private Long[] selectedMsgs;
	
	
}
