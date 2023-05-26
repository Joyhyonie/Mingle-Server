package com.greedy.mingle.attendance.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

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
	public void updateLeaveDoc(Long leaveDocCode, LeaveDocDTO leaveDocDTO){
				
		java.time.LocalDateTime localDateTime = java.time.LocalDateTime.now();
		java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(localDateTime);
		LeaveDoc leaveDoc = leaveDocRepository.findById(leaveDocCode)
				.orElseThrow(() -> new NullPointerException("해당 문서가 존재하지 않습니다."));
		
		Employee employee = employeeRepository.findById(leaveDocDTO.getAccepter().getEmpCode())
				.orElseThrow(() -> new NullPointerException("연차 갯수가 부족합니다."));
		
		String date = leaveDoc.getStartDate();
		String endDate = leaveDoc.getEndDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDate localDate = LocalDate.parse(date, formatter);
		LocalDate localEndDate = LocalDate.parse(endDate, formatter);
		Period period = Period.between(localDate, localEndDate);
		int daysBetween = period.getDays() + 1;		

		if(employee.getEmpAnnual() > 0 && employee.getEmpAnnual() > daysBetween) {
		
		employee.setEmpAnnual(employee.getEmpAnnual() - daysBetween);
		
		leaveDoc.setAccepter(modelMapper.map(leaveDocDTO.getAccepter(), Employee.class));
		leaveDoc.setSignDate(timestamp.toString());
		leaveDoc.setDocStatus("승인");
		
		leaveDocRepository.save(leaveDoc);
		}
	}
	
	@Transactional
	public void updateLeaveDocX(Long leaveDocCode, LeaveDocDTO leaveDocDTO) {
				
		java.time.LocalDateTime localDateTime = java.time.LocalDateTime.now();
		java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(localDateTime);
		LeaveDoc leaveDoc = leaveDocRepository.findById(leaveDocCode)
				.orElseThrow(() -> new NullPointerException("해당 문서가 존재하지 않습니다."));				
		
		leaveDoc.setAccepter(modelMapper.map(leaveDocDTO.getAccepter(), Employee.class));
		leaveDoc.setSignDate(timestamp.toString());
		leaveDoc.setDocStatus("반려");
		
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

	public Page<LeaveDocDTO> selectLeaveDocSearchName(int page, String condition, String name) {

		if(condition.equals("empName")) {
			Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("leaveDocCode").descending());
			Page<LeaveDoc> leaveDocList = leaveDocRepository.findByLeaveApplyerEmpName(pageable, name);		
			Page<LeaveDocDTO> leaveDocDtoFromNameList = leaveDocList.map(leaveDoc -> modelMapper.map(leaveDoc, LeaveDocDTO.class));
			return leaveDocDtoFromNameList;
			} else {
				Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("leaveDocCode").descending());
				Page<LeaveDoc> leaveDocList = leaveDocRepository.findByApplyFormApplyFormNameContaining(pageable, name);
				Page<LeaveDocDTO> leaveDocDTOList = leaveDocList.map(leaveDoc -> modelMapper.map(leaveDoc, LeaveDocDTO.class));
				return leaveDocDTOList;
			}
	}

	public Page<LeaveDocDTO> selectMyLeaveDocSearchName(int page, String condition, String name, Long empCode) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("leaveDocCode").descending());
		Page<LeaveDoc> leaveDocList = leaveDocRepository.findByLeaveApplyerEmpCodeAndApplyFormApplyFormNameContaining(pageable, empCode, name);
		System.out.println(leaveDocList);
		Page<LeaveDocDTO> leaveDocDtoFromNameList = leaveDocList.map(leaveDoc -> modelMapper.map(leaveDoc, LeaveDocDTO.class));
		
		return leaveDocDtoFromNameList;
	}

}
