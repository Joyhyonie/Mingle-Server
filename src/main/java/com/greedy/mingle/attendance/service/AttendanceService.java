package com.greedy.mingle.attendance.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.mingle.attendance.dto.AttendanceDTO;
import com.greedy.mingle.attendance.entity.Attendance;
import com.greedy.mingle.attendance.repository.AttendanceRepository;
import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.employee.repository.EmployeeRepository;

@Service
public class AttendanceService {
	
	private final AttendanceRepository attendanceRepository;
	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	
	public AttendanceService(AttendanceRepository attendanceRepository,ModelMapper modelMapper,EmployeeRepository employeeRepository) {
		this.attendanceRepository = attendanceRepository;
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
	}

	/* 상세 조회 */
	public Page<AttendanceDTO> selectEmpForAdmin(int page,Long empCode) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("atdCode").descending());
		Page<Attendance> attendance = attendanceRepository.findByEmployeeEmpCode(pageable,empCode);
		Page<AttendanceDTO> attendanceDTO = attendance.map(attend -> modelMapper.map(attend, AttendanceDTO.class));
				
		return attendanceDTO;
	}

	/* 근태 수정 */
	@Transactional
	public void updateAttendance(AttendanceDTO attendanceDto) {
		
		Attendance findAttendance = attendanceRepository.findById(attendanceDto.getAtdCode()).orElseThrow(()-> new IllegalArgumentException("근태 코드가 없습니다."));
		findAttendance.update(
				attendanceDto.getAtdDate(), attendanceDto.getAtdStartTime(),
				attendanceDto.getAtdEndTime(),attendanceDto.getAtdStatus(),
				attendanceDto.getAtdEtc());
	}
	
	/* 12시가 되면 자동으로 행 추가 */
	@Transactional
	public void addAttendanceRecord() {
	    System.out.println("살려줘");

	    LocalDateTime now = LocalDateTime.now();
	    List<Employee> employeeList = employeeRepository.findAll();
	    for(Employee employee : employeeList) {
	        Attendance attendance = attendanceRepository.findByEmployeeAndAtdDate(employee, now.toLocalDate().toString());
	        if (attendance == null) {
	            attendance = new Attendance();
	            attendance.setAtdDate(now.toLocalDate().toString());
	            java.sql.Date timestamp = java.sql.Date.valueOf(now.toLocalDate());
	            attendance.setAtdStartTime(timestamp.toString());
	            attendance.setAtdStatus("결근");
	            attendance.setEmployee(employee);
	            attendanceRepository.save(attendance);
	        }
	    }
	}

}
