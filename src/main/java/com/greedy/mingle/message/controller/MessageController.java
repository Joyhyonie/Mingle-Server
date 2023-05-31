package com.greedy.mingle.message.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.common.paging.ResponseDTOWithMorePaging;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.message.dto.MessageDTO;
import com.greedy.mingle.message.service.MessageService;
import com.greedy.mingle.notification.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {
	
	private final MessageService messageService;
	private final NotificationService notiService;
	
	public MessageController(MessageService messageService, NotificationService notiService) {
		this.messageService = messageService;
		this.notiService = notiService;
	}
	
	/* 0. 읽지 않은 쪽지 갯수 조회 */
	@GetMapping("/unread")
	public ResponseEntity<ResponseDTO> selectUnreadMessage(@AuthenticationPrincipal EmployeeDTO receiver) {
		
		int counting = messageService.selectUnreadMessage(receiver.getEmpCode());
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setUnreadMsgs(counting);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "읽지 않은 쪽지 갯수 조회 성공", messageDTO));
	}
	
	
	/* 1. 받은 쪽지함 조회 */
	@GetMapping("/received")
	public ResponseEntity<ResponseDTO> selectReceivedMessage(@AuthenticationPrincipal EmployeeDTO receiver,
															 @RequestParam(name="size", defaultValue="10")int size) {
		
		Page<MessageDTO> messageDTOList = messageService.selectReceivedMessage(receiver.getEmpCode(), size);
		
		ResponseDTOWithMorePaging responseDTOWithMorePaging = new ResponseDTOWithMorePaging();
		responseDTOWithMorePaging.setData(messageDTOList.getContent());
		responseDTOWithMorePaging.setTotalElements(messageDTOList.getTotalElements());
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "받은 쪽지함 조회 성공", responseDTOWithMorePaging));
		
	}
	
	/* 2. 받은 쪽지 클릭 시, 쪽지 읽음 표시 */
	@PatchMapping("/read/{msgCode}")
	public ResponseEntity<ResponseDTO> readMessage(@PathVariable Long msgCode, @AuthenticationPrincipal EmployeeDTO receiver) {
		
		messageService.readMessage(msgCode, receiver.getEmpCode());
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "쪽지 읽음 표시 성공"));
	}
	
	/* 3. 교직원명/내용으로 쪽지 검색 후 조회 (받은 쪽지함) */
	@GetMapping("/received/search")
	public ResponseEntity<ResponseDTO> searchReceivedMessage(@AuthenticationPrincipal EmployeeDTO receiver, 
															 @RequestParam(name="condition")String condition, 
															 @RequestParam(name="word")String word,
															 @RequestParam(name="size", defaultValue="10")int size) {
		
		Page<MessageDTO> messageDTOList = messageService.searchReceivedMessage(receiver.getEmpCode(), condition, word, size);
		
		ResponseDTOWithMorePaging responseDTOWithMorePaging = new ResponseDTOWithMorePaging();
		responseDTOWithMorePaging.setData(messageDTOList.getContent());
		responseDTOWithMorePaging.setTotalElements(messageDTOList.getTotalElements());
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "받은 쪽지함 검색 후 조회 성공", responseDTOWithMorePaging));
		
	}
	
	/* 4. 보낸 쪽지함 조회 */
	@GetMapping("/sent")
	public ResponseEntity<ResponseDTO> selectSentMessage(@AuthenticationPrincipal EmployeeDTO sender,
														 @RequestParam(name="size", defaultValue="10")int size) {
		
		Page<MessageDTO> messageDTOList = messageService.selectSentMessage(sender.getEmpCode(), size);
		
		ResponseDTOWithMorePaging responseDTOWithMorePaging = new ResponseDTOWithMorePaging();
		responseDTOWithMorePaging.setData(messageDTOList.getContent());
		responseDTOWithMorePaging.setTotalElements(messageDTOList.getTotalElements());
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "보낸 쪽지함 조회 성공", responseDTOWithMorePaging));
		
	}
	
	/* 5. 교직원명/내용으로 쪽지 검색 후 조회 (보낸 쪽지함) */
	@GetMapping("/sent/search")
	public ResponseEntity<ResponseDTO> searchSentMessage(@AuthenticationPrincipal EmployeeDTO sender, 
													     @RequestParam(name="condition")String condition, 
														 @RequestParam(name="word")String word,
														 @RequestParam(name="size", defaultValue="10")int size) {
		
		Page<MessageDTO> messageDTOList = messageService.searchSentMessage(sender.getEmpCode(), condition, word, size);
		
		ResponseDTOWithMorePaging responseDTOWithMorePaging = new ResponseDTOWithMorePaging();
		responseDTOWithMorePaging.setData(messageDTOList.getContent());
		responseDTOWithMorePaging.setTotalElements(messageDTOList.getTotalElements());
		
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "보낸 쪽지함 검색 후 조회 성공", responseDTOWithMorePaging));
		
	}
	
	/* 6. 중요 쪽지함 조회 */
	@GetMapping("/liked")
	public ResponseEntity<ResponseDTO> selectlikedMessage(@AuthenticationPrincipal EmployeeDTO employee,
														  @RequestParam(name="size", defaultValue="10")int size) {
		
		Page<MessageDTO> messageDTOList = messageService.selectLikedMessage(employee.getEmpCode(), size);
		
		ResponseDTOWithMorePaging responseDTOWithMorePaging = new ResponseDTOWithMorePaging();
		responseDTOWithMorePaging.setData(messageDTOList.getContent());
		responseDTOWithMorePaging.setTotalElements(messageDTOList.getTotalElements());
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "중요 쪽지함 조회 성공", responseDTOWithMorePaging));
		
	}
	
	/* 7. 교직원명/내용으로 쪽지 검색 후 조회 (중요 쪽지함) */
	@GetMapping("/liked/search")
	public ResponseEntity<ResponseDTO> searchLikedMessage(@AuthenticationPrincipal EmployeeDTO employee, 
														  @RequestParam(name="condition")String condition, 
														  @RequestParam(name="word")String word,
														  @RequestParam(name="size", defaultValue="10")int size) {
		
		Page<MessageDTO> messageDTOList = messageService.searchLikedMessage(employee.getEmpCode(), condition, word, size);
		
		ResponseDTOWithMorePaging responseDTOWithMorePaging = new ResponseDTOWithMorePaging();
		responseDTOWithMorePaging.setData(messageDTOList.getContent());
		responseDTOWithMorePaging.setTotalElements(messageDTOList.getTotalElements());
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "중요 쪽지함 검색 후 조회 성공", responseDTOWithMorePaging));
		
	}
	
	/* 8. 하트 클릭 시, 중요 쪽지함으로 이동 및 취소 */
	@PatchMapping("/like/{msgCode}")
	public ResponseEntity<ResponseDTO> likeToggleMessage(@PathVariable Long msgCode, @AuthenticationPrincipal EmployeeDTO employee) {
		
		messageService.likeToggleMessage(msgCode, employee.getEmpCode());
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "중요 쪽지함으로 이동/취소 성공"));
	}
	
	/* 9. 상위 카테고리가 존재하는 소속 전체 조회 */
	@GetMapping("/find/department")
	public ResponseEntity<ResponseDTO> selectAllDepartment() {
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "현재 존재하는 소속 전체 조회 성공", messageService.selectAllDepartment()));
		
	}
	
	/* 10. 소속 선택 시, 해당 소속 교직원 조회 */
	@GetMapping("/find/employee/{deptCode}")
	public ResponseEntity<ResponseDTO> selectReceiverByDeptCode(@PathVariable Long deptCode) {
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "소속 선택 시, 해당 소속 교직원 조회 성공", messageService.selectReceiverByDeptCode(deptCode)));
		
	}
	
	/* 11. 쪽지 전송 */
	@PostMapping("/send")
	public ResponseEntity<ResponseDTO> sendMessage(@ModelAttribute MessageDTO messageDTO, @AuthenticationPrincipal EmployeeDTO sender) {
		
		messageDTO.setSender(sender);
		messageService.sendMessage(messageDTO);
		
		// 전송 시, 받는 사람에게 알림 전송
		notiService.notifyReceivedMsg(messageDTO);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "쪽지 전송 성공"));
		
	}
	
	/* 12. 선택한 쪽지 삭제 */
	@PatchMapping("/remove")
	public ResponseEntity<ResponseDTO> removeMessage(@RequestBody MessageDTO messageDTO, @AuthenticationPrincipal EmployeeDTO employee) {
		
		Long [] selectedMsgs = messageDTO.getSelectedMsgs();
		
		messageService.removeMessage(selectedMsgs, employee.getEmpCode());
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "선택한 쪽지 삭제 성공"));
		
	}
	

}
