package com.greedy.mingle.notification.service;

// import시 static으로 선언하여 sseEmitters를 해당 클래스에서 사용
import static com.greedy.mingle.notification.controller.SseController.sseEmitters;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import com.greedy.mingle.message.dto.MessageDTO;
import com.greedy.mingle.notification.dto.DeletedNotificationDTO;
import com.greedy.mingle.notification.dto.NotificationDTO;
import com.greedy.mingle.notification.entity.DeletedNotification;
import com.greedy.mingle.notification.entity.Notification;
import com.greedy.mingle.notification.repository.DeletedNotificationRepository;
import com.greedy.mingle.notification.repository.NotificationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationService {

	private final NotificationRepository notiRepository;
	private final DeletedNotificationRepository delNotiRepository;
	private final ModelMapper modelMapper;
	
	public NotificationService (NotificationRepository notiRepository, DeletedNotificationRepository delNotiRepository, ModelMapper modelMapper) {
		this.notiRepository = notiRepository;
		this.delNotiRepository = delNotiRepository;
		this.modelMapper = modelMapper;
	}

	/* 1. 유효한 알림 전체 조회 */
	public List<NotificationDTO> selectMyNoti(Long empCode) {
		
		List<Notification> notiList = notiRepository.findMyNoti(empCode);
		
		List<NotificationDTO> notiDTOList = notiList.stream()
				.map(noti -> modelMapper.map(noti, NotificationDTO.class))
				.collect(Collectors.toList());
		
		return notiDTOList;
	}
	
	/* 2-1. 알림 개별 삭제를 위한 하나의 알림 조회 */
	public NotificationDTO selectOneNoti(Long notiCode) {
		
		Notification noti = notiRepository.findById(notiCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 알림이 없습니다 🥲 notiCode : " + notiCode));
		
		NotificationDTO notiDTO = modelMapper.map(noti, NotificationDTO.class);
		
		return notiDTO;
	}


	/* 2-2. 알림 개별 삭제 */
	@Transactional
	public void removeOneNoti(DeletedNotificationDTO delNotiDTO) {
		
		delNotiRepository.save(modelMapper.map(delNotiDTO, DeletedNotification.class));
		
	}
	
	/* 3. 알림 전체 삭제 */
	// NotificationController에서 foreach로 상단의 removeOneNoti()를 활용
	
	/* 4. 공지사항 등록 시, 알림 등록 */
	// BoardService에서 실행
	
	/* 5. 학사일정 '시작일'/'종료일' 알림 등록 */
	@Transactional
	public void addAcScheduleNoti(NotificationDTO notiDTO) {
	
		notiRepository.save(modelMapper.map(notiDTO, Notification.class));
		
	}

	
	/* ------------------------------------------------------------------------------------------------------- */
	
	/* 1. 쪽지 알림 */
	public synchronized void notifyReceivedMsg(MessageDTO messageDTO) {
		
		String receiverId = messageDTO.getReceiver().getEmpId();
				
		log.info("[NotificationService] notifyReceivedMsg 호출!");
		log.info("[NotificationService] receiverId : {}", receiverId);
		log.info("[NotificationService] sseEmitters : {}", sseEmitters); // => 클라이언트가 새로고침 직후에는 잘 담겨서 넘어오는데 시간이 조금만 지나도 빈 객체로 출력...
		
		if(sseEmitters.containsKey(receiverId)) {
			SseEmitter sseEmitter = sseEmitters.get(receiverId);
			log.info("[NotificationService] sseEmitter: {}", sseEmitter);

			try {
				sseEmitter.send(SseEmitter.event()
						  .name("receivedMsg")
						  .data(messageDTO)
						  .reconnectTime(500));
			} catch (IOException e) {
				sseEmitters.remove(receiverId);
				log.error("[NotificationService] IOException : {}", e.getMessage());
			} catch (IllegalStateException e) {
				sseEmitters.remove(receiverId);
				log.error("[NotificationService] IllegalStateException : {}", e.getMessage());
			}
		}
		
	}
	
	/* 2. 학사일정 알림 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
}
