package com.greedy.mingle.employee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.subject.entity.Department;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	/* 1. 교번으로 교직원 목록 조회 - 페이징 */
	Page<Employee> findAll(Pageable pageable);
	
	/* 2. 교직원 목록 조회 - 소속 기준, 페이징 */
	Page<Employee> findByDepartment(Pageable pageable, Department department);
	
	/* 3. 교직원 목록 조회 - 교직원명 검색 기준, 페이징 */
	Page<Employee> findByEmpName(Pageable pageable, String empName);

	/* 4. 교직원 상세 조회 - empCode로 교직원 1명 조회 */
	@Query("SELECT e " +
			" FROM Employee e " +
			"WHERE e.empCode = :empCode ")
	Optional<Employee> findByEmpCode(@Param("empCode") Long empCode);
	
	/* 5. 교직원 신규 등록 */
	
	/* 6. 교직원 정보 수정 */
	
	/* 7. 교직원 정보 삭제 */
	
	/* 8. 조직도 교직원 조회 - 스크롤 */

	/* 9. 조직도 교직원 조회 - 소속 기준 */

	/* 10. 조직도 교직원 조회 - 교직원명 검색 기준 */
	
	/* 11. 교수인 교직원 조회 - 소속코드 기준 deptCode(11)로 교직원 조회 */
	List<Employee> findByDepartmentRefDeptCode(Long refDeptCode);
	
}
