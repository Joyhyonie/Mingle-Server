package com.greedy.mingle.attendance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.mingle.attendance.entity.Attendance;
import com.greedy.mingle.employee.entity.Employee;

public interface AttendanceRepository extends JpaRepository<Attendance,Long>{

	Page<Attendance> findByEmployeeEmpCode(Pageable pageable,Long empCode);
	
	Attendance findByEmployeeAndAtdDate(Employee employee, String string);


	
}
