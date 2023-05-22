package com.greedy.mingle.attendance.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.mingle.attendance.entity.Attendance;
import com.greedy.mingle.employee.entity.Employee;

public interface AttendanceRepository extends JpaRepository<Attendance,Long>{

	Page<Attendance> findByEmployeeEmpCode(Pageable pageable,Long empCode);
	
	Attendance findByEmployeeAndAtdDate(Employee employee, String string);

	/* 오늘의 출퇴근 기록 조회 */
	@Query(value="SELECT * " + 
				 "FROM TBL_EMP_ATTENDANCE " +
				 "WHERE EMP_CODE = :empCode " +
				 "AND EMP_ATD_DATE >= TO_DATE(:date, 'YYYY-MM-DD') " +
				 "AND EMP_ATD_DATE < TO_DATE(:date, 'YYYY-MM-DD') + 1",
				 nativeQuery = true)
	Attendance findByAtdDateAndEmpCode(@Param("date")String todaysDate, @Param("empCode")Long empCode);
	
	/* 출근 시각 등록 */
	// save() 메소드 활용
	
	/* 퇴근 시각 등록 */
	@Transactional
    @Modifying
    @Query(value="UPDATE TBL_EMP_ATTENDANCE " +
    			 "SET EMP_ATD_END_TIME = TO_DATE(:endTime, 'YYYY-MM-DD HH24:MI:SS') " +
    			 "WHERE EMP_CODE = :empCode " + 
    			 "AND EMP_ATD_DATE >= TO_DATE(:date, 'YYYY-MM-DD') " +
				 "AND EMP_ATD_DATE < TO_DATE(:date, 'YYYY-MM-DD') + 1",
				 nativeQuery = true)
	void updateEndTime(@Param("empCode")Long empCode, @Param("date")String formattedDate, @Param("endTime")String formattedTime);

	
}
