package com.greedy.mingle.employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.mingle.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	/* 아이디로 교직원 조회 메소드 */
	Optional<Employee> findByEmpId(Long empCode);

}
