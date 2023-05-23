package com.greedy.mingle.subject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.mingle.subject.entity.Department;
import com.greedy.mingle.subject.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{

	Page<Subject> findByDepartment(Pageable pageable, Department findDepartment);

	Page<Subject> findBySbjName(Pageable pageable, String sbjName);

	List<Subject> findByDepartmentDeptCode(Long deptCode);

	Page<Subject> findByDepartmentDeptName(Pageable pageable, String name);

}
