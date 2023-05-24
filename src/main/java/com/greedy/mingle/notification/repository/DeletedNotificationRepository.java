package com.greedy.mingle.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.mingle.notification.entity.DeletedNotification;

public interface DeletedNotificationRepository extends JpaRepository<DeletedNotification, Long>{

	/* 2. 알림 개별 삭제 / 3. 알림 전체 삭제 */
	// save() 메소드 활용
	
}
