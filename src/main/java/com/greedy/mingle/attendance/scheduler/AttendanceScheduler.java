package com.greedy.mingle.attendance.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.greedy.mingle.attendance.service.AttendanceService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AttendanceScheduler {

	private AttendanceService attendanceService;
	
	public AttendanceScheduler(AttendanceService attendanceService) {
		this.attendanceService = attendanceService;
	}
	
	@Scheduled(cron = "00 48 09 * * *")
	public void addAttendanceRecord() {
		log.info("ddd");
		attendanceService.addAttendanceRecord();
	}
}
