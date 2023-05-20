package com.greedy.mingle.board.dto;

import java.util.Date;

import com.greedy.mingle.employee.dto.EmployeeDTO;

import lombok.Data;

@Data
public class BoardDTO {

	private Long boardCode;
	private String boardType;
	private String boardTitle;
	private String boardContent;
	private Date boardWriteDate;
	private int boardCount;
	private EmployeeDTO writer;
	private String boardStatus;
	private Date boardModifyDate;
}
