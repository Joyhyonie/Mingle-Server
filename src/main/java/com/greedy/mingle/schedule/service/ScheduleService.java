package com.greedy.mingle.schedule.service;

import java.sql.Date;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.mingle.schedule.dto.ScheduleDTO;
import com.greedy.mingle.schedule.entity.Schedule;
import com.greedy.mingle.schedule.repository.ScheduleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScheduleService {
	
	private final ScheduleRepository scheduleRepository;
	private final ModelMapper modelMapper;
	
	public ScheduleService(ScheduleRepository scheduleRepository, ModelMapper modelMapper) {
		this.scheduleRepository = scheduleRepository;
		this.modelMapper = modelMapper;
	}
	
	/* 1. 선택한 날짜의 나의 일정 조회 */
	public ScheduleDTO selectMySchedule(Date selectedDate, Long empCode) {
		
		Schedule schedule = scheduleRepository.findMyScheduleBySelectedDate(selectedDate, empCode)
				.orElse(new Schedule()); // 해당 날짜의 일정이 없을 경우 빈 객체 반환
		
		return modelMapper.map(schedule, ScheduleDTO.class);
	}
	
	/* 2. 완료된 나의 일정 체크 */
	
	/* 3. 나의 일정 등록 */
	
	/* 4. 나의 일정 수정 */
	
	/* 5. 나의 일정 삭제 */
	
	/* 6. 학사 일정 조회 */
	
}
