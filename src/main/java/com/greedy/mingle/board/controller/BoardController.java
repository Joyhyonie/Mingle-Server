package com.greedy.mingle.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.board.service.BoardService;
import com.greedy.mingle.common.ResponseDTO;

@RestController
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	/* 1. 최신 공지사항 7개 조회 */
	@GetMapping("/preview")
	public ResponseEntity<ResponseDTO> selectBoardPreview() {
		
		String boardStatus = "Y";
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "최신 공지사항 7개 조회 성공", boardService.selectBoardPreview(boardStatus.trim())));
	}
	
	/* 2. 전체 공지사항 조회(페이징) */
	
	/* 3. 카테고리별 공지사항 조회(페이징) */
	
	/* 4. 검색한 공지사항 조회(페이징) */
	
	/* 5. 공지 상세 내용 조회 */
	
	/* 6. 새 공지사항 등록 */
	
	/* 7. 등록된 공지사항 수정 */
	
	/* 8. 등록된 공지사항 삭제 */
	
	/* 9. 공지사항 조회수 증가 */
	
	
	
	
}
