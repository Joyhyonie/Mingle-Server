package com.greedy.mingle.stdAttendance.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.schedule.dto.ScheduleDTO;
import com.greedy.mingle.schedule.service.ScheduleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/attendance")
public class StdAttendanceController {
	
	private final ScheduleService scheduleService;
	
	public StdAttendanceController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
	
//	/*1. 출석정보 조회하기( ) */
//	@GetMapping("")

}
