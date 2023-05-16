package com.greedy.mingle.attendance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.mingle.attendance.entity.Attendance;
import com.greedy.mingle.employee.entity.Employee;

public interface AttendanceRepository extends JpaRepository<Attendance,Long>{

	List<Attendance> findByEmployeeEmpCode(String empCode);

	
	Attendance findByEmployeeAndAtdDate(Employee employee, String string);


	
}
