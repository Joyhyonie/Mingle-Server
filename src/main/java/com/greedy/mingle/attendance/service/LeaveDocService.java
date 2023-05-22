package com.greedy.mingle.attendance.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.mingle.attendance.dto.LeaveDocDTO;
import com.greedy.mingle.attendance.entity.LeaveDoc;
import com.greedy.mingle.attendance.repository.LeaveDocRepository;
import com.greedy.mingle.certi.dto.CertiDocDTO;
import com.greedy.mingle.certi.entity.CertiDoc;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.employee.repository.EmployeeRepository;

@Service
public class LeaveDocService {
	
	private final LeaveDocRepository leaveDocRepository;
	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	
	public LeaveDocService(LeaveDocRepository leaveDocRepository,ModelMapper modelMapper,EmployeeRepository employeeRepository) {
		this.leaveDocRepository = leaveDocRepository;
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
	}

	public Page<LeaveDocDTO> getDoc(int page) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("leaveDocCode").descending());
		Page<LeaveDoc> leaveList = leaveDocRepository.findAll(pageable);
		Page<LeaveDocDTO> leaveDtoList = leaveList.map(leave -> modelMapper.map(leave, LeaveDocDTO.class));
		
		return leaveDtoList;
	}

	@Transactional
	public void updateLeaveDoc(Long leaveDocCode, LeaveDocDTO leaveDocDTO) {
				
		java.time.LocalDateTime localDateTime = java.time.LocalDateTime.now();
		java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(localDateTime);
		LeaveDoc leaveDoc = leaveDocRepository.findById(leaveDocCode)
				.orElseThrow(() -> new NullPointerException("해당 문서가 존재하지 않습니다."));
		
		Employee employee = employeeRepository.findById(leaveDocDTO.getAccepter().getEmpCode())
				.orElseThrow(() -> new NullPointerException("연차 갯수가 부족합니다."));
		employee.setEmpAnnual(employee.getEmpAnnual() - 1);
		
		leaveDoc.setAccepter(modelMapper.map(leaveDocDTO.getAccepter(), Employee.class));
		leaveDoc.setSignDate(timestamp.toString());
		leaveDoc.setDocStatus("승인");
		
		leaveDocRepository.save(leaveDoc);
	}

	@Transactional
	public void registLeaveDoc(LeaveDocDTO leaveDocDTO) {
		
		leaveDocRepository.save(modelMapper.map(leaveDocDTO,LeaveDoc.class));		
	}

	public Page<LeaveDocDTO> selectEmployeeLeave(int page, Long empCode) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("leaveDocCode").descending());
		Page<LeaveDoc> leaveList = leaveDocRepository.findByLeaveApplyerEmpCode(pageable,empCode);
		Page<LeaveDocDTO> leaveDtoList = leaveList.map(leave -> modelMapper.map(leave, LeaveDocDTO.class));

		return leaveDtoList;
	}

}
