package com.greedy.mingle.schedule.service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<ScheduleDTO> selectMySchedule(Date selectedDate, Long empCode) {
		
		List<Schedule> scheduleList = scheduleRepository.findMyScheduleBySelectedDate(selectedDate, empCode);
		List<ScheduleDTO> scheduleDTOList = scheduleList.stream()
		            .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
		            .collect(Collectors.toList());
		
		log.info("[ScheduleService] scheduleDTOList : {}", scheduleDTOList);
		log.info("[ScheduleService] empCode : {}", empCode);
		
		return scheduleDTOList;
	}
	
	/* 2. 완료된 나의 일정 체크 */
	
	/* 3. 나의 일정 등록 */
	
	/* 4. 나의 일정 수정 */
	
	/* 5. 나의 일정 삭제 */
	
	/* 6. 학사 일정 조회 */
	
}
