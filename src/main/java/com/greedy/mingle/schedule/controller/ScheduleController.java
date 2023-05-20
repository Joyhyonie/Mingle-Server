package com.greedy.mingle.schedule.controller;

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
@RequestMapping("/schedule")
public class ScheduleController {
	
	private final ScheduleService scheduleService;
	
	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
	
	/* 1. 나의 일정 전체 조회 */
	@GetMapping("/mine")
	public ResponseEntity<ResponseDTO> selectAllMySchedule(@AuthenticationPrincipal EmployeeDTO employee){
		
		String scheType = "개인";
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "나의 일정 전체 조회 성공", scheduleService.selectAllMySchedule(scheType, employee.getEmpCode())));
	
	}
	
	/* 2. 선택한 날짜의 나의 일정 조회 */
	@GetMapping("/mine/{selectedDate}") 
	public ResponseEntity<ResponseDTO> selectMySchedule(@PathVariable String selectedDate, @AuthenticationPrincipal EmployeeDTO employeeDTO) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = null;
		String scheType = "개인";
		log.info("이것은 포맷되지않은 선택된 날짜 : {}", selectedDate);
		
		try {
			date = dateFormat.parse(selectedDate);
			log.info("이것은 포맷된 선택된 날짜 : {}", date);
		} catch (java.text.ParseException e) {
			return ResponseEntity.badRequest().body(new ResponseDTO(HttpStatus.BAD_REQUEST, "잘못된 날짜 형식입니다."));
		}
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "선택한 날짜의 나의 일정 조회 성공", scheduleService.selectMySchedule(date, scheType, employeeDTO.getEmpCode())));
	
	}
	
	/* 3. 완료 된 나의 일정 체크 */
	@PatchMapping("/check/{scheCode}")
	public ResponseEntity<ResponseDTO> doneMySchedule(@PathVariable Long scheCode) {
		
		scheduleService.doneMySchedule(scheCode);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "완료 된 나의 일정 체크 성공"));
	}
	
	/* 4. 나의 일정 등록 */
	@PostMapping("/mine")
	public ResponseEntity<ResponseDTO> registMySchedule(@RequestBody ScheduleDTO scheduleDTO, @AuthenticationPrincipal EmployeeDTO register) {
		
		scheduleDTO.setEmployee(register);
		scheduleDTO.setScheType("개인");
		scheduleService.registMySchedule(scheduleDTO);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "나의 일정 등록 성공"));
		
	}
	
	/* 5. 나의 일정 수정 */
	@PutMapping("/mine")
	public ResponseEntity<ResponseDTO> modifyMySchedule(@RequestBody ScheduleDTO scheduleDTO) {
		
		scheduleService.modifyMySchedule(scheduleDTO);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "나의 일정 수정 성공"));
	}
	
	/* 6. 나의 일정 삭제 */
	@DeleteMapping("/mine/{scheCode}")
	public ResponseEntity<ResponseDTO> removeMySchedule(@PathVariable Long scheCode) {
		
		scheduleService.removeMySchedule(scheCode);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "나의 일정 삭제 성공"));
		
	}
	
	/* 7. 학사 일정 전체 조회 */
	@GetMapping("/academic")
	public ResponseEntity<ResponseDTO> selectAllAcSchedule(){
		
		String scheType = "학사";
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "학사 일정 전체 조회 성공", scheduleService.selectAllAcSchedule(scheType)));
	
	}
	
	
	/* 8. 선택한 날짜의 학사 일정 조회 */
	@GetMapping("/academic/{selectedDate}") 
	public ResponseEntity<ResponseDTO> selectAcSchedule(@PathVariable String selectedDate) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = null;
		String scheType = "학사";
		
		try {
			date = dateFormat.parse(selectedDate);
		} catch (java.text.ParseException e) {
			return ResponseEntity.badRequest().body(new ResponseDTO(HttpStatus.BAD_REQUEST, "잘못된 날짜 형식입니다."));
		}
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "선택한 날짜의 학사 일정 조회 성공", scheduleService.selectAcSchedule(date, scheType)));
	
	}

}
