package com.greedy.mingle.schedule.controller;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.schedule.service.ScheduleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
	
	private final ScheduleService scheduleService;
	
	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
	
	/* 1. 선택한 날짜의 나의 일정 조회 */
	@GetMapping("/mine/{selectedDate}") 
	public ResponseEntity<ResponseDTO> selectMySchedule(@PathVariable Date selectedDate, @AuthenticationPrincipal EmployeeDTO employeeDto) {

		
		log.info("[ScheduleController] selectedDate : {}", selectedDate);	
		log.info("[ScheduleController] employeeDto : {}", employeeDto);	
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "선택한 날짜의 나의 일정 조회 성공", scheduleService.selectMySchedule(selectedDate, employeeDto.getEmpCode())));
	
	}
	
	/* 2. 완료된 나의 일정 체크 */
	
	/* 3. 나의 일정 등록 */
	
	/* 4. 나의 일정 수정 */
	
	/* 5. 나의 일정 삭제 */
	
	/* 6. 학사 일정 조회 */

}
