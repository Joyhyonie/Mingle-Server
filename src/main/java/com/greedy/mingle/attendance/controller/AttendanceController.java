package com.greedy.mingle.attendance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.attendance.dto.AttendanceDTO;
import com.greedy.mingle.attendance.service.AttendanceService;
import com.greedy.mingle.common.ResponseDTO;

@RestController
@RequestMapping("/attendance")
/* 근태 관리 */
public class AttendanceController {
	
	private final AttendanceService attendanceService;
	
	public AttendanceController(AttendanceService attendanceService) {
		this.attendanceService = attendanceService;
	}
	
	/* 모든 직원 조회 */
	@GetMapping("/list")
	public ResponseEntity<ResponseDTO> selectEmployeeList(@RequestParam(name="page",defaultValue="1") int page){

		
		return null;
		
	}
	
	/* 상세조회 */
	@GetMapping("/list-management/{empCode}")
	public ResponseEntity<ResponseDTO> selectEmployee(@PathVariable Long empCode){
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"조회성공", attendanceService.selectEmpForAdmin(empCode)));
	}
	
	/* 근태 수정 */
	@PutMapping("modify")
	public ResponseEntity<ResponseDTO> updateAttendance(@ModelAttribute AttendanceDTO attendanceDto){
		
		attendanceService.updateAttendance(attendanceDto);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"수정 성공"));
	}

}
