package com.greedy.mingle.message.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.greedy.mingle.message.dto.MessageDTO;
import com.greedy.mingle.message.service.MessageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MessageScheduler {
	
	private final MessageService messageService;
	
	public MessageScheduler (MessageService messageService) {
		this.messageService = messageService;
	}
	
	// 매일 오전 12시마다 휴지통 속 쪽지를 영구 삭제하는 스케줄러
	@Scheduled(cron = "0 0 0 * * *")
	public void autoDeleteMessage() {
		
		log.info("스케줄러는 실행됨!");
		
		List<MessageDTO> messgeList = messageService.selectAllRemovedMessage();
		
		for(MessageDTO message : messgeList) {
			
			if(message.getMsgDelReceiver().equals("Y")) {
				log.info("if문 실행됨!");
				messageService.deleteMessage(message.getMsgCode(), "receiver");
			} else if(message.getMsgDelSender().equals("Y")) {
				log.info("else if문 실행됨!");
				messageService.deleteMessage(message.getMsgCode(), "sender");
			}
			
		}
		
	}

}
