package com.greedy.mingle.subject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.mingle.subject.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
	

	/*refdeptCode에 따른 조회 (개설학과 조회) */
	List<Department> findByRefDeptCode(Long refDeptCode);

}


