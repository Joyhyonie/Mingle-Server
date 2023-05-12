package com.greedy.mingle.attendance.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.mingle.attendance.dto.LeaveDocDTO;
import com.greedy.mingle.attendance.entity.ApplyForm;
import com.greedy.mingle.attendance.entity.LeaveDoc;
import com.greedy.mingle.attendance.repository.LeaveDocRepository;
import com.greedy.mingle.employee.entity.Employee;

@Service
public class LeaveDocService {
	
	private final LeaveDocRepository leaveDocRepository;
	private final ModelMapper modelMapper;
	
	public LeaveDocService(LeaveDocRepository leaveDocRepository,ModelMapper modelMapper) {
		this.leaveDocRepository = leaveDocRepository;
		this.modelMapper = modelMapper;
	}

	public Page<LeaveDocDTO> getDoc(int page) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("leaveDocCode").descending());
		Page<LeaveDoc> leaveList = leaveDocRepository.findAll(pageable);
		Page<LeaveDocDTO> leaveDtoList = leaveList.map(leave -> modelMapper.map(leave, LeaveDocDTO.class));
		
		return leaveDtoList;
	}

	@Transactional
	public void updateLeave(LeaveDocDTO leaveDocDto) {
		
		LeaveDoc findleaveDoc = leaveDocRepository.findById(leaveDocDto.getLeaveDocCode()).orElseThrow(()-> new IllegalArgumentException("신청서 코드가 없습니다."));
		findleaveDoc.update(leaveDocDto.getApplyDate(),leaveDocDto.getSignDate(),leaveDocDto.getDocStatus(),leaveDocDto.getReason(),modelMapper.map(leaveDocDto.getApplyForm(), ApplyForm.class)
				,leaveDocDto.getStartDate(),leaveDocDto.getEndDate(),modelMapper.map(leaveDocDto.getLeaveApplyer(), Employee.class),modelMapper.map(leaveDocDto.getAccepter(), Employee.class)								
				);				
	}

}
