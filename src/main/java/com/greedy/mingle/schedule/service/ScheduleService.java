package com.greedy.mingle.schedule.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
	
	/* 1. 나의 일정 전체 조회 */
	public List<ScheduleDTO> selectAllMySchedule(String scheType, Long empCode) {
		
		List<Schedule> scheduleList = scheduleRepository.findByScheTypeAndEmployee_EmpCode(scheType, empCode);
		
		List<ScheduleDTO> scheduleDTOList = scheduleList.stream()
		            .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
		            .collect(Collectors.toList());
		
		return scheduleDTOList;
	}
	
	
	/* 2. 선택한 날짜의 나의 일정 조회 */
	public List<ScheduleDTO> selectMySchedule(Date date, String scheType, Long empCode) {
		
		List<Schedule> scheduleList = scheduleRepository.findMyScheduleBySelectedDate(date, scheType, empCode);
		
		List<ScheduleDTO> scheduleDTOList = scheduleList.stream()
		            .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
		            .collect(Collectors.toList());
		
		return scheduleDTOList;
		
	}

	/* 3. 완료 된 나의 일정 체크 */
	@Transactional
	public void doneMySchedule(Long scheCode) {
		
		Schedule schedule = scheduleRepository.findById(scheCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 일정이 없습니다 🥲 scheCode : " + scheCode));
		
		String isDone = schedule.getDoneYn();
		
		// 조회된 DoneYn의 값을 Y/N으로 update
		if ("Y".equals(isDone.trim())) {
			schedule.setDoneYn("N");
			scheduleRepository.save(schedule);
		} else {
			schedule.setDoneYn("Y");
			scheduleRepository.save(schedule);
		}
		
	}

	/* 4. 나의 일정 등록 */
	@Transactional
	public void registMySchedule(ScheduleDTO scheduleDTO) {
		
		scheduleRepository.save(modelMapper.map(scheduleDTO, Schedule.class));
		
	}

	/* 5. 나의 일정 수정 */
	@Transactional
	public void modifyMySchedule(ScheduleDTO scheduleDTO) {
		
		Schedule schedule = scheduleRepository.findById(scheduleDTO.getScheCode())
				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 일정이 없습니다 🥲 scheCode : " + scheduleDTO.getScheCode()));
		
		// entity에 정의한 수정용 메소드를 통해 다른 방식의 수정을 방지
		schedule.myScheUpdate(scheduleDTO.getScheName(), 
							  scheduleDTO.getScheStartDate(), 
							  scheduleDTO.getScheEndDate(), 
							  scheduleDTO.getColorCode());
		
	}

	/* 6. 나의 일정 삭제 */
	@Transactional
	public void removeMySchedule(Long scheCode) {
		
		// deleteByIdsms Optional이 아닌 void를 반환하므로 orElseThrow() 메소드 사용 불가 => 해당 코드의 일정 존재 여부 확인 후 deleteById 활용
		Schedule schedule = scheduleRepository.findById(scheCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 일정이 없습니다 🥲 scheCode : " + scheCode));
		
		scheduleRepository.deleteById(scheCode);
		
	}
	
	/* 7. 학사 일정 전체 조회 */
	public Object selectAllAcSchedule(String scheType) {
		
		List<Schedule> scheduleList = scheduleRepository.findByScheType(scheType);
		
		List<ScheduleDTO> scheduleDTOList = scheduleList.stream()
		            .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
		            .collect(Collectors.toList());
		
		return scheduleDTOList;
	}

	/* 8. 선택한 날짜의 학사 일정 조회 */
	public Object selectAcSchedule(Date date, String scheType) {
		
		List<Schedule> scheduleList = scheduleRepository.findAcScheduleBySelectedDate(date, scheType);
		
		List<ScheduleDTO> scheduleDTOList = scheduleList.stream()
		            .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
		            .collect(Collectors.toList());
		
		return scheduleDTOList;
	}
	
	/* 9. 학사 일정 등록 */
	@Transactional
	public void registAcSchedule(ScheduleDTO scheduleDTO) {
		
		scheduleRepository.save(modelMapper.map(scheduleDTO, Schedule.class));
		
	}
	
	/* 10. 나의 일정 삭제 */
	@Transactional
	public void deleteAcSchedule(Long scheCode) {
		
		Schedule schedule = scheduleRepository.findById(scheCode)
				.orElseThrow(() -> new IllegalArgumentException("학사 일정 scheCode : " + scheCode));
		
		scheduleRepository.deleteById(scheCode);
		
	}
	
	/* 11. 학사 일정 수정 */
	@Transactional
	public void modifyAcSchedule(ScheduleDTO scheduleDTO) {
		
		Schedule schedule = scheduleRepository.findById(scheduleDTO.getScheCode())
				.orElseThrow(() -> new IllegalArgumentException("학사 일정 조회 실패 : " + scheduleDTO.getScheCode()));
		
		// entity에 정의한 수정용 메소드를 통해 다른 방식의 수정을 방지
		schedule.acScheUpdate(scheduleDTO.getScheName(), 
							  scheduleDTO.getScheStartDate(), 
							  scheduleDTO.getScheEndDate(),
							  scheduleDTO.getScheContent());
		
	}

	

	
}
