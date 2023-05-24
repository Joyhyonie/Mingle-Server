package com.greedy.mingle.notification.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.greedy.mingle.notification.service.NotificationService;
import com.greedy.mingle.schedule.dto.ScheduleDTO;
import com.greedy.mingle.schedule.entity.Schedule;
import com.greedy.mingle.schedule.service.ScheduleService;

@Component
public class NotificationScheduler {
	
	private final ScheduleService scheduleService;
	private final NotificationService notiService;
	
	public NotificationScheduler(ScheduleService scheduleService, NotificationService notiService) {
		this.scheduleService = scheduleService;
		this.notiService = notiService;
	}

	// 매일 오전 9시에 실행되는 스케줄러
    @Scheduled(cron = "0 0 9 * * *")
    public void checkSchedule() {
        // 현재 날짜
        LocalDate today = LocalDate.now();
        
        // 전체 학사일정의 시작일/종료일 조회
        String scheType = "학사";
        List<ScheduleDTO> scheduleList = scheduleService.selectAllAcSchedule(scheType);
        
        for (ScheduleDTO schedule : scheduleList) {
            // 조회된 시작일과 종료일을 현재 날짜와 비교
            if (schedule.getScheStartDate().equals(today)) {
                // 시작일과 현재 날짜가 일치하는 경우, 알림 테이블에 Insert
            	notiService.addAcStartNoti(schedule);
            }
            
            if (schedule.getScheEndDate().equals(today)) {
                // 종료일과 현재 날짜가 일치하는 경우, 알림 테이블에 Insert
            	notiService.addAcEndNoti(schedule);
            }
        }
    }
}
