package com.greedy.mingle.schedule.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.mingle.schedule.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	
	/* 1. 선택한 날짜의 나의 일정 조회 */
	@Query("SELECT s "
	        + "FROM Schedule s "
	        + "JOIN fetch s.employee "
	        + "WHERE :selectedDate BETWEEN s.scheStartDate AND s.scheEndDate "
	        + "AND s.employee.empCode = :empCode")
	List<Schedule> findMyScheduleBySelectedDate(@Param("selectedDate")Date selectedDate, @Param("empCode")Long empCode);
	
	/* 2. 완료된 나의 일정 체크 */
	
	/* 3. 나의 일정 등록 */
	
	/* 4. 나의 일정 수정 */
	
	/* 5. 나의 일정 삭제 */
	
	/* 6. 학사 일정 조회 */
	
}
