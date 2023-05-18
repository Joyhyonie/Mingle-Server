package com.greedy.mingle.student.dto;

import java.util.Date;

import com.greedy.mingle.subject.dto.DepartmentDTO;

import lombok.Data;

@Data
public class StudentDTO {

	private Long stdCode;
	private String stdNameEn;
	private String stdPwd;
	private String stdName;
	private String stdEmail;
	private String stdPhone;
	private String stdAddress;
	private String stdProfile;
	private Date stdEntDate;
	private Date stdAbDate;
	private Date stdDropDate;
	private Date stdLeaveDate;
	private String stdStatus;
	private Long stdLevel;
	private String stdSsn;
	private DepartmentDTO department;
}
