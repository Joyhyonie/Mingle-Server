package com.greedy.mingle.student.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.student.entity.Student;
import com.greedy.mingle.subject.entity.Department;

public interface StudentRepository extends JpaRepository<Student, Long> {

	/* 1. 학번으로 학생 목록 조회 - 페이징 */
	Page<Student> findAll(Pageable pageable);

	
	/* 2. 학생 목록 조회 - 학과 기준, 페이징 */
	Page<Student> findByDepartment(Pageable pageable, Department department);
	
	/* 3. 학생 목록 조회 - 학생명 검색 기준, 페이징 */
	Page<Student> findByStdName(Pageable pageable, String stdName);
	
	Page<Student> findByDepartmentDeptName(Pageable pageable, String name);

	/* 4. 학생 상세 조회 - stdCode로 학생 1명 조회 */
	@Query("SELECT s" +
			" FROM Student s " +
			"WHERE s.stdCode = :stdCode ")
	Optional<Student> findByStdCode(@Param("stdCode") Long stdCode);
	
}
