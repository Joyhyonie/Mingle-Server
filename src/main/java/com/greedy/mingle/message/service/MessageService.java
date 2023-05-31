package com.greedy.mingle.message.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.employee.repository.EmployeeRepository;
import com.greedy.mingle.message.dto.MessageDTO;
import com.greedy.mingle.message.entity.Message;
import com.greedy.mingle.message.repository.MessageRepository;
import com.greedy.mingle.subject.dto.DepartmentDTO;
import com.greedy.mingle.subject.entity.Department;
import com.greedy.mingle.subject.repository.DepartmentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageService {

	private final MessageRepository messageRepository;
	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	
	
	public MessageService(MessageRepository messageRepository, DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper) {
		this.messageRepository = messageRepository;
		this.departmentRepository = departmentRepository;
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
	}
	
	/* 0. 읽지 않은 쪽지 갯수 조회 */
	public int selectUnreadMessage(Long empCode) {
		
		int counting = messageRepository.countUnreadMessage(empCode);
		
		return counting;
	}

	/* 1. 받은 쪽지함 조회 */
	public Page<MessageDTO> selectReceivedMessage(Long empCode, int size) {
		
		Pageable pageable = PageRequest.of(0, size, Sort.by("msgCode").descending());
		
		Page<Message> messageList = messageRepository.findReceivedMessage(empCode, pageable);
		
		Page<MessageDTO> messageDTOList = messageList.map(message -> modelMapper.map(message, MessageDTO.class));
		
		return messageDTOList;
	}

	/* 2. 받은 쪽지 클릭 시, 쪽지 읽음 표시 */
	@Transactional
	public void readMessage(Long msgCode, Long empCode) {
		
		Message message = messageRepository.findById(msgCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 쪽지가 없습니다 🥲 msgCode : " + msgCode));
		
		message.readMessage("Y");
		
	}

	/* 3. 교직원명/내용으로 쪽지 검색 후 조회 (받은 쪽지함) */
	public Page<MessageDTO> searchReceivedMessage(Long empCode, String condition, String word, int size) {
		
		Pageable pageable = PageRequest.of(0, size, Sort.by("msgCode").descending());
		
		Page<Message> messageList;
		
		if(condition.equals("empName")) {
			messageList = messageRepository.findReceivedMessageBySender(empCode, word, pageable);
		} else {
			messageList = messageRepository.findReceivedMessageByContent(empCode, word, pageable);
		}
		
		Page<MessageDTO> messageDTOList = messageList.map(message -> modelMapper.map(message, MessageDTO.class));
		
		return messageDTOList;
	}
	
	/* 4. 보낸 쪽지함 조회 */
	public Page<MessageDTO> selectSentMessage(Long empCode, int size) {
		
		Pageable pageable = PageRequest.of(0, size, Sort.by("msgCode").descending());
		
		Page<Message> messageList = messageRepository.findSentMessage(empCode, pageable);
		
		Page<MessageDTO> messageDTOList = messageList.map(message -> modelMapper.map(message, MessageDTO.class));
		
		return messageDTOList;
	}

	/* 5. 교직원명/내용으로 쪽지 검색 후 조회 (보낸 쪽지함) */
	public Page<MessageDTO> searchSentMessage(Long empCode, String condition, String word, int size) {
		
		Pageable pageable = PageRequest.of(0, size, Sort.by("msgCode").descending());
		
		Page<Message> messageList;
		
		if(condition.equals("empName")) {
			messageList = messageRepository.findSentMessageByReceiver(empCode, word, pageable);
		} else {
			messageList = messageRepository.findSentMessageByContent(empCode, word, pageable);
		}
		
		Page<MessageDTO> messageDTOList = messageList.map(message -> modelMapper.map(message, MessageDTO.class));
		
		return messageDTOList;
		
	}

	/* 6. 중요 쪽지함 조회 */
	public Page<MessageDTO> selectLikedMessage(Long empCode, int size) {
		
		Pageable pageable = PageRequest.of(0, size, Sort.by("msgCode").descending());
		
		Page<Message> messageList = messageRepository.findLikedMessage(empCode, pageable);
		
		Page<MessageDTO> messageDTOList = messageList.map(message -> modelMapper.map(message, MessageDTO.class));
		
		return messageDTOList;
		
	}

	/* 7. 교직원명/내용으로 쪽지 검색 후 조회 (중요 쪽지함) */
	public Page<MessageDTO> searchLikedMessage(Long empCode, String condition, String word, int size) {
		
		Pageable pageable = PageRequest.of(0, size, Sort.by("msgCode").descending());
		
		Page<Message> messageList;
		
		if(condition.equals("empName")) {
			messageList = messageRepository.findLikedMessageByEmployee(empCode, word, pageable);
		} else {
			messageList = messageRepository.findLikedMessageByContent(empCode, word, pageable);
		}
		
		Page<MessageDTO> messageDTOList = messageList.map(message -> modelMapper.map(message, MessageDTO.class));
		
		return messageDTOList;
		
	}

	/* 8. 하트 클릭 시, 중요 쪽지함으로 이동 및 취소 */
	@Transactional
	public void likeToggleMessage(Long msgCode, Long empCode) {
		
		Message message = messageRepository.findById(msgCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 쪽지가 없습니다 🥲 msgCode : " + msgCode));
		
		// 현재 유저가 receiver/sender인지 판별하기 위해 현재 쪽지의 receiver/sender empCode 추출
		Long receiverEmpCode = message.getReceiver().getEmpCode();
		Long senderEmpCode = message.getSender().getEmpCode();
		
		// 해당 유저가 receiver일 경우,
		if(receiverEmpCode.equals(empCode)) {
			
			String isLike = message.getMsgImpReceiver();
			
			if ("Y".equals(isLike.trim())) {
				message.setMsgImpReceiver("N");
				messageRepository.save(message);
			} else {
				message.setMsgImpReceiver("Y");
				messageRepository.save(message);
			}
			
		// 해당 유저가 sender일 경우,
		} else if(senderEmpCode.equals(empCode)) {
			
			String isLike = message.getMsgImpSender();
			
			if ("Y".equals(isLike.trim())) {
				message.setMsgImpSender("N");
				messageRepository.save(message);
			} else {
				message.setMsgImpSender("Y");
				messageRepository.save(message);
			}
		} 
		
	}
	
	/* 9. 상위 카테고리가 존재하는 소속 전체 조회 */
	public List<DepartmentDTO> selectAllDepartment() {
		
		List<Department> departmentList = departmentRepository.findByRefDeptCodeIsNotNull();
		
		List<DepartmentDTO> departmentDTOList = departmentList.stream()
				.map(department -> modelMapper.map(department, DepartmentDTO.class))
				.collect(Collectors.toList());
		
		return departmentDTOList;
	}

	/* 10. 소속 선택 시, 해당 소속 교직원 조회 */
	public List<EmployeeDTO> selectReceiverByDeptCode(Long deptCode) {
		
		List<Employee> employeeList = employeeRepository.findByDepartmentDeptCode(deptCode);
		
		List<EmployeeDTO> employeeDTOList = employeeList.stream()
				.map(employee -> modelMapper.map(employee, EmployeeDTO.class))
				.collect(Collectors.toList());
		
		return employeeDTOList;
		
	}

	/* 11. 쪽지 전송 */
	@Transactional
	public void sendMessage(MessageDTO messageDTO) {
		
		messageRepository.save(modelMapper.map(messageDTO, Message.class));
		
	}

	/* 12. 선택한 쪽지 삭제 */
	public void removeMessage(Long[] selectedMsgs, Long empCode) {
		
		for (Long msgCode : selectedMsgs) {
			
			// 선택한 쪽지들이 존재하는지 확인
			Message message = messageRepository.findById(msgCode)
					.orElseThrow(() -> new IllegalArgumentException("해당 코드의 쪽지가 없습니다 🥲 msgCode : " + msgCode));
			
			// 현재 유저가 receiver/sender인지 판별하기 위해 현재 쪽지의 receiver/sender empCode 추출
			Long receiverEmpCode = message.getReceiver().getEmpCode();
			Long senderEmpCode = message.getSender().getEmpCode();
			
			// 해당 유저가 receiver일 경우,
			if(receiverEmpCode.equals(empCode)) {
				message.setMsgDelReceiver("Y");
				messageRepository.save(message);
				
			// 해당 유저가 sender일 경우,
			} else if(senderEmpCode.equals(empCode)) {
				message.setMsgDelSender("Y");
				messageRepository.save(message);
			}
			
		} 
			
	}

	
		
	
}
