package com.greedy.mingle.schedule.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.mingle.schedule.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	
	/* 1. 나의 일정 전체 조회 */
	List<Schedule> findByScheTypeAndEmployee_EmpCode(String scheType, Long empCode);
	
	/* 2. 선택한 날짜의 나의 일정 조회 */
	@Query("SELECT s "
	        + "FROM Schedule s "
	        + "JOIN fetch s.employee "
	        + "WHERE :selectedDate BETWEEN s.scheStartDate AND s.scheEndDate "
	        + "AND s.employee.empCode = :empCode "
	        + "AND s.scheType = :scheType")
	List<Schedule> findMyScheduleBySelectedDate(@Param("selectedDate") Date date, @Param("scheType")String scheType, @Param("empCode")Long empCode);
	
	/* 3. 완료 된 나의 일정 체크 */
	// save() 메소드 활용
	
	/* 4. 나의 일정 등록 */
	// save() 메소드 활용
	
	/* 5. 나의 일정 수정 */
	// findById() 메소드로 조회 후 필드 값 수정 시, 변화를 감지하여 update 구문 자동 생성
	
	/* 6. 나의 일정 삭제 */
	// deleteById() 메소드 활용
	
	/* 7. 학사 일정 전체 조회 */
	List<Schedule> findByScheType(String scheType, Sort sort);
	
	/* 8. 선택한 날짜의 학사 일정 조회 */
	@Query("SELECT s "
	        + "FROM Schedule s "
	        + "WHERE :selectedDate BETWEEN s.scheStartDate AND s.scheEndDate "
	        + "AND s.scheType = :scheType")
	List<Schedule> findAcScheduleBySelectedDate(@Param("selectedDate")Date date, @Param("scheType")String scheType);

	
}
