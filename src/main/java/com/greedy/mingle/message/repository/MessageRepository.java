package com.greedy.mingle.message.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.message.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

	/* 1. 받은 쪽지함 조회 (최근 20개) */
	@Query(value="SELECT * " +
				 "FROM ( SELECT * " + 
						 "FROM TBL_MESSAGE " + 
						 "WHERE MSG_RECEIVER = :empCode " + 
						 "AND MSG_IMP_RECEIVER = 'N' " + 
						 "AND MSG_DEL_RECEIVER = 'N' " + 
						 "ORDER BY MSG_SEND_DATE DESC ) " + 
				 "WHERE ROWNUM <= 20 ",
				 nativeQuery = true)
	List<Message> findReceivedMessage(@Param("empCode")Long empCode);
	
	/* 2. 받은 쪽지 클릭 시, 쪽지 읽음 표시 */
	// Entity에 정의한 readMessage()로 update
	
	/* 3-1. 교직원명으로 쪽지 검색 후 조회 (받은 쪽지함) */
	@Query(value="SELECT m " +
				 "FROM Message m " +
				 "JOIN fetch m.sender " +
				 "JOIN fetch m.receiver " +
				 "WHERE m.receiver.empCode = :empCode " +
				 "AND m.msgImpReceiver = 'N' " + 
				 "AND m.msgDelReceiver = 'N' " +
				 "AND m.sender.empName LIKE %:word%")
	List<Message> findReceivedMessageBySender(@Param("empCode")Long empCode, @Param("word")String word);
	
	/* 3-2. 내용으로 쪽지 검색 후 조회 (받은 쪽지함) */
	@Query(value="SELECT m " +
			 "FROM Message m " +
			 "WHERE m.receiver.empCode = :empCode " +
			 "AND m.msgImpReceiver = 'N' " + 
			 "AND m.msgDelReceiver = 'N' " +
			 "AND m.msgContent LIKE %:word%")
	List<Message> findReceivedMessageByContent(@Param("empCode")Long empCode, @Param("word")String word);

	/* 4. 보낸 쪽지함 조회 (최근 20개) */
	@Query(value="SELECT * " +
				 "FROM ( SELECT * " + 
						 "FROM TBL_MESSAGE " + 
						 "WHERE MSG_SENDER = :empCode " + 
						 "AND MSG_IMP_RECEIVER = 'N' " + 
						 "AND MSG_DEL_RECEIVER = 'N' " + 
						 "ORDER BY MSG_SEND_DATE DESC ) " + 
				 "WHERE ROWNUM <= 20 ",
				 nativeQuery = true)
	List<Message> findSentMessage(@Param("empCode")Long empCode);

	/* 4-1. 교직원명으로 쪽지 검색 후 조회 (보낸 쪽지함) */
	@Query(value="SELECT m " +
			 "FROM Message m " +
			 "JOIN fetch m.receiver " +
			 "JOIN fetch m.sender " +
			 "WHERE m.sender.empCode = :empCode " +
			 "AND m.msgImpReceiver = 'N' " + 
			 "AND m.msgDelReceiver = 'N' " +
			 "AND m.receiver.empName LIKE %:word%")
	List<Message> findSentMessageBySender(@Param("empCode")Long empCode, @Param("word")String word);

	/* 4-2. 내용으로 쪽지 검색 후 조회 (보낸 쪽지함) */
	@Query(value="SELECT m " +
			 "FROM Message m " +
			 "WHERE m.sender.empCode = :empCode " +
			 "AND m.msgImpReceiver = 'N' " + 
			 "AND m.msgDelReceiver = 'N' " +
			 "AND m.msgContent LIKE %:word%")
	List<Message> findSentMessageByContent(@Param("empCode")Long empCode, @Param("word")String word);

	/* 5. 중요 쪽지함 조회 (전체) */
	@Query(value="SELECT * " +
				 "FROM TBL_MESSAGE " +
				 "WHERE (MSG_SENDER = :empCode AND MSG_IMP_SENDER = 'Y' AND MSG_DEL_SENDER = 'N') " +
				 "OR (MSG_RECEIVER = :empCode AND MSG_IMP_RECEIVER = 'Y' AND MSG_DEL_RECEIVER = 'N') ",
				 nativeQuery = true)
	List<Message> findLikedMessage(@Param("empCode")Long empCode);

	/* 5-1. 교직원명으로 쪽지 검색 후 조회 (중요 쪽지함) */
	@Query(value="SELECT m " +
				 "FROM Message m " +
				 "JOIN fetch m.receiver " +
				 "JOIN fetch m.sender " + 
				 "WHERE (m.sender.empCode = :empCode AND m.msgImpSender = 'Y' AND m.msgDelSender = 'N' AND m.receiver.empName LIKE %:word%) " +
				 "OR (m.receiver.empCode = :empCode AND m.msgImpReceiver = 'Y' AND m.msgDelReceiver = 'N' AND m.sender.empName LIKE %:word%)")
	List<Message> findLikedMessageBySender(@Param("empCode")Long empCode, @Param("word")String word);

	/* 5-2. 내용으로 쪽지 검색 후 조회 (중요 쪽지함) */
	@Query(value="SELECT m " +
			 "FROM Message m " +
			 "JOIN fetch m.receiver " +
			 "JOIN fetch m.sender " + 
			 "WHERE (m.sender.empCode = :empCode AND m.msgImpSender = 'Y' AND m.msgDelSender = 'N' AND m.msgContent LIKE %:word%) " +
			 "OR (m.receiver.empCode = :empCode AND m.msgImpReceiver = 'Y' AND m.msgDelReceiver = 'N' AND m.msgContent LIKE %:word%)")
	List<Message> findLikedMessageByContent(Long empCode, String word);
	
	
	/* 6. 하트 클릭 시, 중요 쪽지함으로 이동 및 취소 */
	// save() 메소드 활용
	
	/* 7. 상위 카테고리가 존재하는 소속 전체 조회 */
	// DepartmentRepository에서 findByRefDeptCodeIsNotNull() 쿼리메소드 사용
	
	/* 8. 소속 선택 시, 해당 소속 교직원 조회 */
	// EmployeeRepository에서 findByDepartmentDeptCode() 쿼리메소드 사용
	
	/* 9. 쪽지 전송 */
	// save() 메소드 활용
	
	/* 10. 선택한 쪽지 삭제 */
	// save() 메소드 활용

}
