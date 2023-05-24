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
	         + "        (SELECT dn.notification.notiCode FROM DeletedNotification dn WHERE dn.employee.empCode = :empCode )"
	         + "    )"
	         + ")")
	List<Notification> findMyNoti(@Param("empCode")Long empCode);
	
	/* 4. 공지사항 등록 시, 알림 등록 */
	
	/* 5. 학사일정 '시작일' 알림 등록 */
	
	/* 6. 학사일정 '종료일' 알림 등록 */

}
