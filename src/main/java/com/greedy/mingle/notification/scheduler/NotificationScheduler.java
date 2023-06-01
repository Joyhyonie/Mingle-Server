package com.greedy.mingle.notification.scheduler;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.greedy.mingle.notification.dto.NotificationDTO;
import com.greedy.mingle.notification.dto.NotificationTypeDTO;
import com.greedy.mingle.notification.service.NotificationService;
import com.greedy.mingle.schedule.dto.ScheduleDTO;
import com.greedy.mingle.schedule.service.ScheduleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NotificationScheduler {
	
	private final ScheduleService scheduleService;
	private final NotificationService notiService;
	
	public NotificationScheduler(ScheduleService scheduleService, NotificationService notiService) {
		this.scheduleService = scheduleService;
		this.notiService = notiService;
	}

	// 매일 오전 9시 45분에 실행되는 스케줄러
    @Scheduled(cron = "0 45 9 * * *")
    public void checkSchedule() {
    	
        // 현재 날짜
        LocalDate today = LocalDate.now();
        log.info("[NotificationScheduler] today : {}", today);
        
        // 전체 학사일정의 시작일/종료일 조회
        String scheType = "학사";
        List<ScheduleDTO> scheduleList = scheduleService.selectAllAcSchedule(scheType);
        
        for (ScheduleDTO schedule : scheduleList) {
        	
        	// 조건문에서 비교하기 위해 TIMESTAMP를 LocalDate 형식으로 포맷
        	LocalDate startDate = new java.sql.Date(schedule.getScheStartDate().getTime()).toLocalDate();
        	LocalDate endDate = new java.sql.Date(schedule.getScheEndDate().getTime()).toLocalDate();
        	
        	// 학사일정의 알림 유효시간은 하루
    		Date now = new Date();
    		Date tomorrow = new Date(now.getTime() + (1000 * 60 * 60 * 24));
        	
            // 조회된 시작일과 종료일을 현재 날짜와 비교
            if (startDate.equals(today)) {
                /* 시작일과 현재 날짜가 일치하는 경우, 알림 테이블에 Insert */
            	
        		// 시작일 notiType 설정
        		NotificationTypeDTO notiTypeDTO = new NotificationTypeDTO();
        		notiTypeDTO.setNotiTypeCode((long) 40002);
        		notiTypeDTO.setNotiTitle("오늘은 해당 학사일정의 시작일입니다.");
        		// 알림
        		NotificationDTO notiDTO = new NotificationDTO();
        		notiDTO.setNotiContent(schedule.getScheName());
        		notiDTO.setNotiEndDate(tomorrow);
        		notiDTO.setNotiType(notiTypeDTO);
        		
            	notiService.addNoti(notiDTO);
        		notiService.notifyCommonNoti(notiDTO); // 학사일정 시작일 알림 등록 시, 모든 교직원에게 실시간 알림 전송
            	
            } else if (endDate.equals(today)) {
                /* 종료일과 현재 날짜가 일치하는 경우, 알림 테이블에 Insert */
            	
            	// 종료일 notiType 설정
        		NotificationTypeDTO notiTypeDTO = new NotificationTypeDTO();
        		notiTypeDTO.setNotiTypeCode((long) 40003);
        		notiTypeDTO.setNotiTitle("오늘은 해당 학사일정의 종료일입니다.");
        		// 알림
        		NotificationDTO notiDTO = new NotificationDTO();
        		notiDTO.setNotiContent(schedule.getScheName());
        		notiDTO.setNotiEndDate(tomorrow);
        		notiDTO.setNotiType(notiTypeDTO);
        		
            	notiService.addNoti(notiDTO);
            	notiService.notifyCommonNoti(notiDTO); // 학사일정 종료일 알림 등록 시, 모든 교직원에게 실시간 알림 전송
            	
            }
            
        }
        
    }
    
}
