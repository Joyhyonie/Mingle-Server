package com.greedy.mingle.student.dto;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date stdEntDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date stdAbDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date stdDropDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date stdLeaveDate;
	private String stdStatus;
	private Long stdLevel;
	private String stdSsn;
	private DepartmentDTO department;
}
