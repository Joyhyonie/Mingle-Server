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
	@Scheduled(cron = "0 10 1 * * *")
	public void autoDeleteMessage() {
		
		List<MessageDTO> messgeList = messageService.selectAllRemovedMessage();
		
		for(MessageDTO message : messgeList) {
			
			if(message.getMsgDelReceiver().equals("Y")) {
				messageService.deleteMessage(message.getMsgCode(), "receiver");
			} else if(message.getMsgDelSender().equals("Y")) {
				messageService.deleteMessage(message.getMsgCode(), "sender");
			}
			
		}
		
	}

}
