package com.greedy.mingle.message.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.greedy.mingle.message.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
	
	/* 1. 읽지 않은 쪽지 갯수 조회 */
	@Query(value="SELECT COUNT(m.msgReadYn) "
			   + "FROM Message m "
			   + "JOIN m.receiver "
			   + "WHERE m.receiver.empCode = :empCode "
			   + "AND m.msgReadYn = 'N' ")
	int countUnreadMessage(@Param("empCode")Long empCode);

	/* 2. 받은 쪽지함 조회 (최근 20개) */
	@Query(value="SELECT m " +
				 "FROM Message m " + 
				 "WHERE m.receiver.empCode = :empCode " + 
				 "AND m.msgImpReceiver = 'N' " + 
				 "AND m.msgDelReceiver = 'N' ")
	Page<Message> findReceivedMessage(@Param("empCode")Long empCode, Pageable pageable);
	
	/* 3. 받은 쪽지 클릭 시, 쪽지 읽음 표시 */
	// Entity에 정의한 readMessage()로 update
	
	/* 4-1. 교직원명으로 쪽지 검색 후 조회 (받은 쪽지함) */
	@Query(value="SELECT m " +
				 "FROM Message m " +
				 "JOIN m.sender " +
				 "JOIN m.receiver " +
				 "WHERE m.receiver.empCode = :empCode " +
				 "AND m.msgImpReceiver = 'N' " + 
				 "AND m.msgDelReceiver = 'N' " +
				 "AND m.sender.empName LIKE %:word%")
	Page<Message> findReceivedMessageBySender(@Param("empCode")Long empCode, @Param("word")String word, Pageable pageable);
	
	/* 4-2. 내용으로 쪽지 검색 후 조회 (받은 쪽지함) */
	@Query(value="SELECT m " +
				 "FROM Message m " +
				 "WHERE m.receiver.empCode = :empCode " +
				 "AND m.msgImpReceiver = 'N' " + 
				 "AND m.msgDelReceiver = 'N' " +
				 "AND m.msgContent LIKE %:word%")
	Page<Message> findReceivedMessageByContent(@Param("empCode")Long empCode, @Param("word")String word, Pageable pageable);

	/* 5. 보낸 쪽지함 조회 */
	@Query(value="SELECT m " +
			 "FROM Message m " + 
			 "WHERE m.sender.empCode = :empCode " + 
			 "AND m.msgImpSender = 'N' " + 
			 "AND m.msgDelSender = 'N' ")
	Page<Message> findSentMessage(@Param("empCode")Long empCode, Pageable pageable);

	/* 6-1. 교직원명으로 쪽지 검색 후 조회 (보낸 쪽지함) */
	@Query(value="SELECT m " +
			 "FROM Message m " +
			 "JOIN m.receiver " +
			 "JOIN m.sender " +
			 "WHERE m.sender.empCode = :empCode " +
			 "AND m.msgImpSender = 'N' " + 
			 "AND m.msgDelSender = 'N' " +
			 "AND m.receiver.empName LIKE %:word%")
	Page<Message> findSentMessageByReceiver(@Param("empCode")Long empCode, @Param("word")String word, Pageable pageable);

	/* 6-2. 내용으로 쪽지 검색 후 조회 (보낸 쪽지함) */
	@Query(value="SELECT m " +
			 "FROM Message m " +
			 "WHERE m.sender.empCode = :empCode " +
			 "AND m.msgImpSender = 'N' " + 
			 "AND m.msgDelSender = 'N' " +
			 "AND m.msgContent LIKE %:word%")
	Page<Message> findSentMessageByContent(@Param("empCode")Long empCode, @Param("word")String word, Pageable pageable);

	/* 7. 중요 쪽지함 조회 */
	@Query(value="SELECT m "
			   + "FROM Message m "
			   + "WHERE (m.sender.empCode = :empCode AND msgImpSender = 'Y' AND msgDelSender = 'N') "
			   + "OR (m.receiver.empCode = :empCode AND msgImpReceiver = 'Y' AND msgDelReceiver = 'N') ")
	Page<Message> findLikedMessage(@Param("empCode")Long empCode, Pageable pageable);

	/* 8-1. 교직원명으로 쪽지 검색 후 조회 (중요 쪽지함) */
	@Query(value="SELECT m " +
				 "FROM Message m " +
				 "JOIN m.receiver " +
				 "JOIN m.sender " + 
				 "WHERE (m.sender.empCode = :empCode AND m.msgImpSender = 'Y' AND m.msgDelSender = 'N' AND m.receiver.empName LIKE %:word%) " +
				 "OR (m.receiver.empCode = :empCode AND m.msgImpReceiver = 'Y' AND m.msgDelReceiver = 'N' AND m.sender.empName LIKE %:word%) ")
	Page<Message> findLikedMessageByEmployee(@Param("empCode")Long empCode, @Param("word")String word, Pageable pageable);

	/* 8-2. 내용으로 쪽지 검색 후 조회 (중요 쪽지함) */
	@Query(value="SELECT m " +
			 "FROM Message m " +
			 "JOIN m.receiver " +
			 "JOIN m.sender " + 
			 "WHERE (m.sender.empCode = :empCode AND m.msgImpSender = 'Y' AND m.msgDelSender = 'N' AND m.msgContent LIKE %:word%) " +
			 "OR (m.receiver.empCode = :empCode AND m.msgImpReceiver = 'Y' AND m.msgDelReceiver = 'N' AND m.msgContent LIKE %:word%) ")
	Page<Message> findLikedMessageByContent(Long empCode, String word, Pageable pageable);
	
	/* 9. 휴지통 조회 */
	@Query(value="SELECT m "
			   + "FROM Message m "
			   + "WHERE (m.sender.empCode = :empCode AND msgDelSender = 'Y') "
			   + "OR (m.receiver.empCode = :empCode AND msgDelReceiver = 'Y') ")
	Page<Message> findRemovedMessage(@Param("empCode")Long empCode, Pageable pageable);

	
	/* 10. 하트 클릭 시, 중요 쪽지함으로 이동 및 취소 */
	// save() 메소드 활용
	
	/* 11. 상위 카테고리가 존재하는 소속 전체 조회 */
	// DepartmentRepository에서 findByRefDeptCodeIsNotNull() 쿼리메소드 사용
	
	/* 12. 소속 선택 시, 해당 소속 교직원 조회 */
	// EmployeeRepository에서 findByDepartmentDeptCode() 쿼리메소드 사용
	
	/* 13. 쪽지 전송 */
	// save() 메소드 활용
	
	/* 14. 선택한 쪽지 삭제 */
	// save() 메소드 활용
	
	/* 15. 선택한 쪽지 복구 */
	// save() 메소드 활용
	
	/* 16. 휴지통에 존재하는 쪽지 전체 조회 */
	List<Message> findByMsgDelSenderOrMsgDelReceiver(String msgDelSender, String msgDelReceiver);
	
	/* 17. 쪽지 영구 삭제 */
	// save() 메소드 활용

}
