package com.greedy.mingle.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.mingle.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	/* 1. 최신 공지사항 7개 조회 */
	List<Board> findTop7ByBoardStatusOrderByBoardCodeDesc(String boardStatus);

	/* 2. 전체 공지사항 조회(페이징) */
	Page<Board> findByBoardStatus(String string, Pageable pageable);

	/* 3-1. 분류별 공지사항 조회(페이징) */
	Page<Board> findByBoardTypeAndBoardStatus(String type, String boardStatus, Pageable pageable);
	
	/* 3-2. 분류 및 제목별 공지사항 조회(페이징) */
	@Query(value="SELECT b "
			   + "FROM Board b "
			   + "WHERE (:type = '전체' OR boardType = :type) "
			   + "AND b.boardTitle LIKE %:word% "
			   + "AND b.boardStatus = 'Y' ")
	Page<Board> findByBoardTitle(@Param("type")String type, @Param("word")String word, Pageable pageable);

	/* 3-3. 분류 및 내용별 공지사항 조회(페이징) */
	@Query(value="SELECT b "
			   + "FROM Board b "
			   + "WHERE (:type = '전체' OR boardType = :type) "
			   + "AND b.boardContent LIKE %:word% "
			   + "AND b.boardStatus = 'Y' ")
	Page<Board> findByBoardContent(@Param("type")String type,  @Param("word")String word, Pageable pageable);

	/* 3-4. 분류 및 작성자별 공지사항 조회(페이징) */
	@Query(value="SELECT b "
	           + "FROM Board b "
	           + "JOIN b.writer "
	           + "WHERE (:type = '전체' OR b.boardType = :type) "
	           + "AND b.writer.empName LIKE %:word% "
	           + "AND b.boardStatus = 'Y' ")
	Page<Board> findByWriter(@Param("type")String type,  @Param("word")String word, Pageable pageable);

	/* 4. 공지 상세 내용 조회 */
	Optional<Board> findByBoardCode(@Param("boardCode")Long boardCode);

	
	/* 5. 새 공지사항 등록 */
	// save() 메소드 활용
	
	/* 6. 등록된 공지사항 수정 */
	// boardUpdate() 메소드 활용
	
	/* 7. 등록된 공지사항 삭제 */
	// save() 메소드 활용
	
	/* 8. 공지사항 조회수 증가 */
	// save() 메소드 활용

}
