package com.greedy.mingle.attendance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.mingle.attendance.entity.LeaveDoc;
import com.greedy.mingle.employee.entity.Employee;

public interface LeaveDocRepository extends JpaRepository<LeaveDoc, Long>{

	Page<LeaveDoc> findByLeaveApplyerEmpCode(Pageable pageable, Long empCode);

	LeaveDoc findByLeaveApplyer(Employee employee);

	Page<LeaveDoc> findByLeaveApplyerEmpName(Pageable pageable, String name);

	Page<LeaveDoc> findByApplyFormApplyFormNameContaining(Pageable pageable, String name);

	Page<LeaveDoc> findByLeaveApplyerEmpCodeAndApplyFormApplyFormNameContaining(Pageable pageable, Long empCode, String name);

}
