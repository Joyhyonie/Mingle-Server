package com.greedy.mingle.attendance.controller;

import org.springframework.data.domain.Page;
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
import com.greedy.mingle.common.paging.Pagenation;
import com.greedy.mingle.common.paging.PagingButtonInfo;
import com.greedy.mingle.common.paging.ResponseDTOWithPaging;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.employee.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/attendance")
/* 근태 관리 */
public class AttendanceController {
	
	private final AttendanceService attendanceService;
	private final EmployeeService employeeService;
	
	public AttendanceController(AttendanceService attendanceService,EmployeeService employeeService) {
		this.attendanceService = attendanceService;
		this.employeeService = employeeService;
	}
	
	/* 모든 직원 조회 */
	@GetMapping("/list")
	public ResponseEntity<ResponseDTO> selectEmployeeList(@RequestParam(name="page",defaultValue="1") int page){

		Page<EmployeeDTO> employeeDtoList = employeeService.selectEmployeeList(page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(employeeDtoList);
		
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(employeeDtoList.getContent());
		
		log.info("[EmployeeController] : selectEmployeeList end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));

		
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
