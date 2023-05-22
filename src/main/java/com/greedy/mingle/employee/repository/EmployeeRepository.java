package com.greedy.mingle.employee.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.employee.entity.EmployeeRole;
import com.greedy.mingle.subject.entity.Department;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	/* 1. 교번으로 교직원 목록 조회 - 페이징 */
	Page<Employee> findAll(Pageable pageable);
	
	/* 2. 교직원 목록 조회 - 소속 기준, 페이징 */
	Page<Employee> findByDepartment(Pageable pageable, Department findDepartment);
	
	/* 3. 교직원 목록 조회 - 교직원명 검색 기준, 페이징 */
	Page<Employee> findByEmpName(Pageable pageable, String empName);

	/* 4. 교직원 상세 조회 - empCode로 교직원 1명 조회 */
	@Query("SELECT e " +
			" FROM Employee e " +
			"WHERE e.empCode = :empCode ")
	@EntityGraph(attributePaths= {"department","empRole", "empRole.auth"})
	Optional<Employee> findByEmpCode(@Param("empCode") Long empCode);
	
	
	
	/* 5. 교직원 신규 등록 */

	/* 6. 교직원 정보 수정 */
	
	/* 7. 교직원 정보 삭제 */
	
	/* 8. 조직도 교직원 조회 - 스크롤 */

	/* 9. 조직도 교직원 조회 - 소속 기준 */

	/* 10. 조직도 교직원 조회 - 교직원명 검색 기준 */
	
	/* 11. 특정 학과의 교수인 교직원 조회 - 소속코드 기준 deptCode로 교직원 조회 */
	List<Employee> findByDepartmentDeptCode(Long deptCode);

	Optional<Employee> findByEmpNameAndEmpPhone(String empName, String empPhone);
	
	 Employee findByEmpIdAndEmpEmail(String empId, String empEmail);
	
	   List<Employee> findByEmpNameContains(String searchKeyword);


	

	Optional<Employee> findByEmpNameAndEmpEmail(String empName, String empEmail);

	Optional<Employee> findByEmpId(String empId);

	

	
	
}
