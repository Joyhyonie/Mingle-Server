package com.greedy.mingle.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.mingle.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	
	/* 1. 최신 공지사항 7개 조회 */
	List<Board> findTop7ByBoardStatusOrderByBoardWriteDateDesc(String boardStatus);
	
	/* 2. 전체 공지사항 조회(페이징) */
	
	/* 3. 카테고리별 공지사항 조회(페이징) */
	
	/* 4. 검색한 공지사항 조회(페이징) */
	
	/* 5. 공지 상세 내용 조회 */
	
	/* 6. 새 공지사항 등록 */
	
	/* 7. 등록된 공지사항 수정 */
	
	/* 8. 등록된 공지사항 삭제 */
	
	/* 9. 공지사항 조회수 증가 */

}
