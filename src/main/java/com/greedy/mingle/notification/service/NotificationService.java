package com.greedy.mingle.notification.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.mingle.notification.dto.NotificationDTO;
import com.greedy.mingle.notification.entity.Notification;
import com.greedy.mingle.notification.repository.NotificationRepository;
import com.greedy.mingle.schedule.dto.ScheduleDTO;

@Service
public class NotificationService {

	private final NotificationRepository notiRepository;
	private final ModelMapper modelMapper;
	
	public NotificationService (NotificationRepository notiRepository, ModelMapper modelMapper) {
		this.notiRepository = notiRepository;
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

	/* 2. 알림 개별 삭제 */
	
	/* 3. 알림 전체 삭제 */
	
	/* 4. 공지사항 등록 시, 알림 등록 */
	// BoardService에서 실행
	
	/* 5. 학사일정 '시작일' 알림 등록 */
	public void addAcStartNoti(ScheduleDTO schedule) {
		// TODO Auto-generated method stub
		
	}

	/* 6. 학사일정 '종료일' 알림 등록 */
	public void addAcEndNoti(ScheduleDTO schedule) {
		// TODO Auto-generated method stub
		
	}
	
}
