package com.greedy.mingle.attendance.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.attendance.dto.AttendanceDTO;
import com.greedy.mingle.attendance.dto.LeaveDocDTO;
import com.greedy.mingle.attendance.service.AttendanceService;
import com.greedy.mingle.attendance.service.LeaveDocService;
import com.greedy.mingle.certi.dto.CertiDocDTO;
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
	private final LeaveDocService leaveDocService;
	private final EmployeeService employeeService;
	
	public AttendanceController(AttendanceService attendanceService,EmployeeService employeeService,LeaveDocService leaveDocService) {
		this.attendanceService = attendanceService;
		this.leaveDocService = leaveDocService;
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
	
	/* 학과 및 소속으로 검색 */
	@GetMapping("/department/{deptCode}")
	public ResponseEntity<ResponseDTO> selectEmployeeListByDepartment(
			@RequestParam(name="page", defaultValue="1") int page, @PathVariable Long deptCode){
		
		Page<EmployeeDTO> employeeDtoList = employeeService.selectEmployeeListByDepartment(page, deptCode);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(employeeDtoList);
		
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(employeeDtoList.getContent());
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	/* 학과 및 소속명으로 검색 */
	@GetMapping("/search")
	public ResponseEntity<ResponseDTO> selectEmployeeListSearchName(
			@RequestParam(name="page", defaultValue="1") int page,	
			@RequestParam(name="condition")String condition,
			@RequestParam(name="search") String name){
		
		Page<EmployeeDTO> employeeDTOList = employeeService.selectEmployeeListByDeptName(page, condition, name);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(employeeDTOList);

		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(employeeDTOList.getContent());
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	/* 휴가신청서종류 및 신청자 이름으로 검색 */
	@GetMapping("/leaveDocSearch")
	public ResponseEntity<ResponseDTO> selectLeaveDocSearchName(
			@RequestParam(name="page", defaultValue="1") int page,	
			@RequestParam(name="condition")String condition,
			@RequestParam(name="search") String name){
		
		Page<LeaveDocDTO> LeaveDocDTOList = leaveDocService.selectLeaveDocSearchName(page, condition, name);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(LeaveDocDTOList);

		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(LeaveDocDTOList.getContent());
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	/* 나의 휴가신청내역종류 이름으로 검색 */
	@GetMapping("/myLeaveDocSearch")
	public ResponseEntity<ResponseDTO> selectMyLeaveDocSearchName(
			@RequestParam(name="page", defaultValue="1") int page,	
			@RequestParam(name="condition")String condition,
			@RequestParam(name="search") String name,
			@AuthenticationPrincipal EmployeeDTO employee){
		
		Page<LeaveDocDTO> LeaveDocDTOList = leaveDocService.selectMyLeaveDocSearchName(page, condition, name,employee.getEmpCode());
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(LeaveDocDTOList);

		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(LeaveDocDTOList.getContent());
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	
	/* 상세조회 */
	@GetMapping("/list-management/{empCode}")
	public ResponseEntity<ResponseDTO> selectEmployee(@RequestParam(name="page", defaultValue="1") int page,@PathVariable Long empCode){
		
		Page<AttendanceDTO> attendanceDetail = attendanceService.selectEmpForAdmin(page,empCode); 
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(attendanceDetail);
		
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(attendanceDetail.getContent());
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"조회성공",responseDtoWithPaging));
	}
	
	
	
	/* 휴가 신청 조회 */
	@GetMapping("/leave/list")
	public ResponseEntity<ResponseDTO> getLeaveDoc(@RequestParam(name="page",defaultValue="1") int page){
		
		Page<LeaveDocDTO> leaveDocDtoList = leaveDocService.getDoc(page);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(leaveDocDtoList);
		
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(leaveDocDtoList.getContent());
		
		log.info("[SubjectController : responseDtoWithPaging : {}",responseDtoWithPaging);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공",responseDtoWithPaging));
	}
	
	/* 휴가 신청 내여 조회 후 승인 */
	@PatchMapping("/update/{leaveDocCode}")
	public ResponseEntity<ResponseDTO> updateLeave(@PathVariable Long leaveDocCode,@RequestBody LeaveDocDTO leaveDocDTO,@AuthenticationPrincipal EmployeeDTO employee){
		
		leaveDocDTO.setAccepter(employee);
		leaveDocService.updateLeaveDoc(leaveDocCode, leaveDocDTO);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "수정 성공"));
	}
	
	/* 휴가 신청 내역 조회 후 반려 */
	@PatchMapping("/noUpdate/{leaveDocCode}")
	public ResponseEntity<ResponseDTO> noUpdateLeave(@PathVariable Long leaveDocCode,@RequestBody LeaveDocDTO leaveDocDTO,@AuthenticationPrincipal EmployeeDTO employee){
		
		leaveDocDTO.setAccepter(employee);
		leaveDocService.updateLeaveDocX(leaveDocCode, leaveDocDTO);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "수정 성공"));
	}
  
  /* 휴가 신청 */
	@PostMapping("/regist")
	public ResponseEntity<ResponseDTO> registLeave(@AuthenticationPrincipal EmployeeDTO employee,
			@ModelAttribute LeaveDocDTO leaveDocDTO){
		
		leaveDocDTO.setLeaveApplyer(employee);
		leaveDocService.registLeaveDoc(leaveDocDTO);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"등록 성공"));
	}
	
	/* 내 휴가 신청 내역 조회 */
	@GetMapping("/leave/myLeave")
	public ResponseEntity<ResponseDTO> getMyLeave(@RequestParam(name="page",defaultValue="1") int page,
			@AuthenticationPrincipal EmployeeDTO employee){
		
		Page<LeaveDocDTO> leaveDocDTO = leaveDocService.selectEmployeeLeave(page,employee.getEmpCode());
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(leaveDocDTO);
		
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(leaveDocDTO.getContent());
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"조회 성공",responseDtoWithPaging));
	}
	
	/* 내 근태 내역 조회 */
	@GetMapping("/myAttendance")
	public ResponseEntity<ResponseDTO> getMyAttendance(@RequestParam(name="page",defaultValue="1") int page,
			@AuthenticationPrincipal EmployeeDTO employee){
		
		Page<AttendanceDTO> attendanceDTO = attendanceService.getMyAttendance(page,employee.getEmpCode());
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(attendanceDTO);
		
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(attendanceDTO.getContent());
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"조회 성공",responseDtoWithPaging));
		
	}
	
	/* 근태 수정 */
	@PutMapping("/modify")
	public ResponseEntity<ResponseDTO> updateAttendance(@ModelAttribute AttendanceDTO attendanceDto){

		attendanceService.updateAttendance(attendanceDto);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"수정 성공"));
	}
	
	/* 근태 수정 */
	@PatchMapping("/updateAdmin/{atdCode}")
	public ResponseEntity<ResponseDTO> updateAttendance(@PathVariable Long atdCode,@ModelAttribute AttendanceDTO attendanceDTO){
		
		attendanceService.updateAttendance(atdCode, attendanceDTO);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "수정 성공"));
	}

	/* 오늘의 출퇴근 기록 조회 */
	@GetMapping("/today")
	public ResponseEntity<ResponseDTO> selectTodayAttendance(@AuthenticationPrincipal EmployeeDTO employee) {
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String todaysDate = now.format(timeFormat);
		log.info("[AttendanceController] 현재 날짜 : {}", todaysDate);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "오늘의 출퇴근 여부 조회 성공", attendanceService.selectTodayAttendance(todaysDate, employee.getEmpCode())));
	}
	

	/* 출근 시각 등록 */
	@PostMapping("/record")
	public ResponseEntity<ResponseDTO> recordStartTime(AttendanceDTO attendanceDTO, @AuthenticationPrincipal EmployeeDTO employee) {
		
		attendanceDTO.setEmployee(employee);
		attendanceService.recordStartTime(attendanceDTO);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "출근 시각 등록 성공"));
		
	}
	
	/* 퇴근 시각 등록 */
	@PatchMapping("/record")
	public ResponseEntity<ResponseDTO> recordEndTime(@AuthenticationPrincipal EmployeeDTO employee) {
		
		LocalDateTime now = LocalDateTime.now();
		
		// 현재 날짜 포맷 (String)
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = now.format(dateFormat);
		
		// 현재 시간 포맷 (String)
		Timestamp timestamp = Timestamp.valueOf(now);
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedTime = timeFormat.format(timestamp);
		
		log.info("[AttendanceController] 클라이언트에서 요청한 현재 시간 : {}", formattedTime);
		log.info("[AttendanceController] 클라이언트가 요청한 날짜 : {}", formattedDate);
		
		attendanceService.recordEndTime(employee.getEmpCode(), formattedDate, formattedTime);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "퇴근 시각 등록 성공"));
	}



}
