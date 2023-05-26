package com.greedy.mingle.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.mingle.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	/* 1. 유효한 알림 전체 조회 */
	@Query(value="SELECT n, nt "
	         + "FROM Notification n "
	         + "JOIN fetch n.notiType nt "
	         + "WHERE n.notiEndDate >= SYSDATE "
	         + "AND (n.notiCode NOT IN (SELECT dn.notification.notiCode FROM DeletedNotification dn) OR "
	         + "    (n.notiCode IN (SELECT dn.notification.notiCode FROM DeletedNotification dn) AND n.notiCode NOT IN "
	         + "        (SELECT dn.notification.notiCode FROM DeletedNotification dn WHERE dn.employee.empCode = :empCode ) "
	         + "    ) "
	         + ") "
	         + "ORDER BY n.notiCode DESC ")
	List<Notification> findMyNoti(@Param("empCode")Long empCode);
	
	/* 2-1. 알림 개별 삭제를 위한 하나의 알림 조회 */
	// findById() 메소드 활용
	
	/* 2-2. 알림 개별 삭제 */
	// save() 메소드 활용
	
	/* 4. 공지사항 등록 시, 알림 등록 */
	// save() 메소드 활용
	
	/* 5. 학사일정 시작일&종료일 알림 등록 */
	// save() 메소드 활용
}
