package com.greedy.mingle.message.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.message.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {
	
	private final MessageService messageService;
	
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	/* 받은 쪽지함 조회 (최근 20개) */
	@GetMapping("/received")
	public ResponseEntity<ResponseDTO> selectReceivedMessage(@AuthenticationPrincipal EmployeeDTO receiver) {
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "받은 쪽지함 조회 성공", messageService.selectReceivedMessage(receiver.getEmpCode())));
		
	}
	
	/* 받은 쪽지 클릭 시, 쪽지 읽음 표시 */
	@PatchMapping("/read/{msgCode}")
	public ResponseEntity<ResponseDTO> readMessage(@PathVariable Long msgCode, @AuthenticationPrincipal EmployeeDTO receiver) {
		
		messageService.readMessage(msgCode, receiver.getEmpCode());
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "쪽지 읽음 표시 성공"));
	}
	
	/* 교직원명/내용으로 쪽지 검색 후 조회 (받은 쪽지함) */
	@GetMapping("/received/search")
	public ResponseEntity<ResponseDTO> searchReceivedMessage(@AuthenticationPrincipal EmployeeDTO receiver, 
															 @RequestParam(name="condition")String condition, 
															 @RequestParam(name="word")String word) {
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "받은 쪽지함 검색 후 조회 성공", messageService.searchReceivedMessage(receiver.getEmpCode(), condition, word)));
		
	}
	
	/* 보낸 쪽지함 조회 (최근 20개) */
	@GetMapping("/sent")
	public ResponseEntity<ResponseDTO> selectSentMessage(@AuthenticationPrincipal EmployeeDTO sender) {
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "보낸 쪽지함 조회 성공", messageService.selectSentMessage(sender.getEmpCode())));
		
	}
	
	/* 교직원명/내용으로 쪽지 검색 후 조회 (보낸 쪽지함) */
	@GetMapping("/sent/search")
	public ResponseEntity<ResponseDTO> searchSentMessage(@AuthenticationPrincipal EmployeeDTO sender, 
													     @RequestParam(name="condition")String condition, 
														 @RequestParam(name="word")String word) {
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "보낸 쪽지함 검색 후 조회 성공", messageService.searchSentMessage(sender.getEmpCode(), condition, word)));
		
	}
	
	/* 중요 쪽지함 조회 (전체) */
	@GetMapping("/liked")
	public ResponseEntity<ResponseDTO> selectlikedMessage(@AuthenticationPrincipal EmployeeDTO employee) {
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "중요 쪽지함 조회 성공", messageService.selectLikedMessage(employee.getEmpCode())));
		
	}
	
	/* 교직원명/내용으로 쪽지 검색 후 조회 (중요 쪽지함) */
	@GetMapping("/liked/search")
	public ResponseEntity<ResponseDTO> searchLikedMessage(@AuthenticationPrincipal EmployeeDTO employee, 
															 @RequestParam(name="condition")String condition, 
															 @RequestParam(name="word")String word) {
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "중요 쪽지함 검색 후 조회 성공", messageService.searchLikedMessage(employee.getEmpCode(), condition, word)));
		
	}
	
	/* 하트 클릭 시, 중요 쪽지함으로 이동 및 취소 */
	
	/* 소속 선택 시, 해당 소속 교직원 조회 */
	
	/* 받는 사람 선택 및 내용 작성 후 쪽지 전송 */
	
	/* 선택한 쪽지 삭제 */
	

}
