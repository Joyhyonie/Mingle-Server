package com.greedy.mingle.subject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.mingle.subject.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
	

	/*refdeptCode에 따른 조회 (개설학과 조회) */
	List<Department> findByRefDeptCode(Long refDeptCode);

	/* 상위 카테고리가 존재하는 소속 전체 조회 ('쪽지'에서 사용) */
	List<Department> findByRefDeptCodeIsNotNull();

}


