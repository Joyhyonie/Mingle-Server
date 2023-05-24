package com.greedy.mingle.notification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.notification.service.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {
	
	private final NotificationService notiService;
	
	public NotificationController(NotificationService notiService) {
		this.notiService = notiService;
	}
	
	/* 1. 유효한 알림 전체 조회 */
	@GetMapping("/all")
	public ResponseEntity<ResponseDTO> selectMyNoti(@AuthenticationPrincipal EmployeeDTO employee) {
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "유효한 알림 전체 조회 성공", notiService.selectMyNoti(employee.getEmpCode())));
	}
	
	/* 2. 알림 개별 삭제 */
	
	/* 3. 알림 전체 삭제 */

}
