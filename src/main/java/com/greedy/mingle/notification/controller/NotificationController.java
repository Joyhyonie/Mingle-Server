package com.greedy.mingle.notification.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.notification.dto.DeletedNotificationDTO;
import com.greedy.mingle.notification.dto.NotificationDTO;
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
	@PostMapping("/remove/{notiCode}")
	public ResponseEntity<ResponseDTO> removeOneNoti(@AuthenticationPrincipal EmployeeDTO employee, @PathVariable(value="notiCode")Long notiCode) {
		
		// 전달 받은 notiCode를 조회한 후 delNotiDTO에 set
		NotificationDTO noti = notiService.selectOneNoti(notiCode);
		
		DeletedNotificationDTO delNotiDTO = new DeletedNotificationDTO();
		delNotiDTO.setEmployee(employee);
		delNotiDTO.setNotification(noti);
		notiService.removeOneNoti(delNotiDTO);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "알림 개별 삭제 성공"));
	}
	
	/* 3. 알림 전체 삭제 */
	@PostMapping("/remove/all")
	public ResponseEntity<ResponseDTO> removeAllNoti(@AuthenticationPrincipal EmployeeDTO employee) {
		
		// 현재 유저의 유효한 알림 전체를 우선 조회
		List<NotificationDTO> notiList = notiService.selectMyNoti(employee.getEmpCode());
		
		DeletedNotificationDTO delNotiDTO = new DeletedNotificationDTO();
		
		for(NotificationDTO noti : notiList) {
			delNotiDTO.setEmployee(employee);
			delNotiDTO.setNotification(noti);
			notiService.removeOneNoti(delNotiDTO);
		}
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "알림 전체 삭제 성공"));
	}
	
	
	
	
	

}
