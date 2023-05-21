package com.greedy.mingle.message.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.mingle.message.dto.MessageDTO;
import com.greedy.mingle.message.entity.Message;
import com.greedy.mingle.message.repository.MessageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageService {

	private final MessageRepository messageRepository;
	private final ModelMapper modelMapper;
	
	public MessageService(MessageRepository messageRepository, ModelMapper modelMapper) {
		this.messageRepository = messageRepository;
		this.modelMapper = modelMapper;
	}

	/* 받은 쪽지함 조회 (최근 20개) */
	public List<MessageDTO> selectReceivedMessage(Long empCode) {
		
		List<Message> messageList = messageRepository.findReceivedMessage(empCode);
		
		List<MessageDTO> messageDTOList = messageList.stream()
				.map(message -> modelMapper.map(message, MessageDTO.class))
				.collect(Collectors.toList());
		
		return messageDTOList;
	}

	/* 받은 쪽지 클릭 시, 쪽지 읽음 표시 */
	@Transactional
	public void readMessage(Long msgCode, Long empCode) {
		
		Message message = messageRepository.findById(msgCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 쪽지가 없습니다 🥲 msgCode : " + msgCode));
		
		message.readMessage("Y");
		
	}

	/* 교직원명/내용으로 쪽지 검색 후 조회 (받은 쪽지함) */
	public Object searchReceivedMessage(Long empCode, String condition, String word) {
		
		if(condition.equals("empName")) {
			
			List<Message> messageList = messageRepository.findReceivedMessageBySender(empCode, word);
			
			List<MessageDTO> messageDTOList = messageList.stream()
					.map(message -> modelMapper.map(message, MessageDTO.class))
					.collect(Collectors.toList());
			
			return messageDTOList;
			
		} else {
			
			List<Message> messageList = messageRepository.findReceivedMessageByContent(empCode, word);
			
			List<MessageDTO> messageDTOList = messageList.stream()
					.map(message -> modelMapper.map(message, MessageDTO.class))
					.collect(Collectors.toList());
			
			return messageDTOList;
			
		}
	}
	
	/* 보낸 쪽지함 조회 (최근 20개) */
	public List<MessageDTO> selectSentMessage(Long empCode) {
		
		List<Message> messageList = messageRepository.findSentMessage(empCode);
		
		List<MessageDTO> messageDTOList = messageList.stream()
				.map(message -> modelMapper.map(message, MessageDTO.class))
				.collect(Collectors.toList());
		
		return messageDTOList;
	}

	/* 교직원명/내용으로 쪽지 검색 후 조회 (보낸 쪽지함) */
	public List<MessageDTO> searchSentMessage(Long empCode, String condition, String word) {
		
		if(condition.equals("empName")) {
			
			List<Message> messageList = messageRepository.findSentMessageBySender(empCode, word);
			
			List<MessageDTO> messageDTOList = messageList.stream()
					.map(message -> modelMapper.map(message, MessageDTO.class))
					.collect(Collectors.toList());
			
			return messageDTOList;
			
		} else {
			
			List<Message> messageList = messageRepository.findSentMessageByContent(empCode, word);
			
			List<MessageDTO> messageDTOList = messageList.stream()
					.map(message -> modelMapper.map(message, MessageDTO.class))
					.collect(Collectors.toList());
			
			return messageDTOList;
			
		}
	}

	/* 중요 쪽지함 조회 (전체) */
	public List<MessageDTO> selectLikedMessage(Long empCode) {
		
		List<Message> messageList = messageRepository.findLikedMessage(empCode);
		
		List<MessageDTO> messageDTOList = messageList.stream()
				.map(message -> modelMapper.map(message, MessageDTO.class))
				.collect(Collectors.toList());
		
		return messageDTOList;
	}

	/* 교직원명/내용으로 쪽지 검색 후 조회 (중요 쪽지함) */
	public List<MessageDTO> searchLikedMessage(Long empCode, String condition, String word) {
		
		if(condition.equals("empName")) {
			
			List<Message> messageList = messageRepository.findLikedMessageBySender(empCode, word);
			
			List<MessageDTO> messageDTOList = messageList.stream()
					.map(message -> modelMapper.map(message, MessageDTO.class))
					.collect(Collectors.toList());
			
			return messageDTOList;
			
		} else {
			
			List<Message> messageList = messageRepository.findLikedMessageByContent(empCode, word);
			
			List<MessageDTO> messageDTOList = messageList.stream()
					.map(message -> modelMapper.map(message, MessageDTO.class))
					.collect(Collectors.toList());
			
			return messageDTOList;
			
		}
		
	}
	
	/* 하트 클릭 시, 중요 쪽지함으로 이동 및 취소 */
	
	/* 소속 선택 시, 해당 소속 교직원 조회 */
	
	/* 받는 사람 선택 및 내용 작성 후 쪽지 전송 */
	
	/* 선택한 쪽지 삭제 */
	
	
}
