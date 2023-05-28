package com.greedy.mingle.board.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.board.dto.BoardDTO;
import com.greedy.mingle.board.service.BoardService;
import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.common.paging.Pagenation;
import com.greedy.mingle.common.paging.PagingButtonInfo;
import com.greedy.mingle.common.paging.ResponseDTOWithPaging;

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
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "최신 공지사항 7개 조회 성공", boardService.selectBoardPreview()));
	}
	
	/* 2. 전체 공지사항 조회(페이징) */
	@GetMapping("/list")
	public ResponseEntity<ResponseDTO> selectBoardList(@RequestParam(name="page", defaultValue="1") int page) {
		
		Page<BoardDTO> boardDTOList = boardService.selectBoardList(page);
		
		// 페이징 처리
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(boardDTOList);
		
		ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
		responseDTOWithPaging.setPageInfo(pageInfo);
		responseDTOWithPaging.setData(boardDTOList.getContent()); // DTOList에서 Content를 꺼내서 set
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "전체 공지사항 조회 성공", responseDTOWithPaging));
	}
	
	/* 3. 분류 및 검색기준별 공지사항 조회(페이징) */
	@GetMapping("/search")
	public ResponseEntity<ResponseDTO> selectSearchedBoard(@RequestParam(name="page", defaultValue="1") int page,
														   @RequestParam(name="type", defaultValue="전체") String type,
														   @RequestParam(name="condition") String condition,
														   @RequestParam(name="word") String word) {
		
		Page<BoardDTO> boardDTOList = boardService.selectSearchedBoard(page, type, condition, word);
		
		// 페이징 처리
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(boardDTOList);
		
		ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
		responseDTOWithPaging.setPageInfo(pageInfo);
		responseDTOWithPaging.setData(boardDTOList.getContent()); // DTOList에서 Content를 꺼내서 set
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "카테고리 및 검색한 공지사항 조회 성공", responseDTOWithPaging));
	}
	
	/* 4. 공지 상세 내용 조회 */
	@GetMapping("/{boardCode}")
	public ResponseEntity<ResponseDTO> selectBoardDetail(@PathVariable Long boardCode) {
	
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "공지 상세 내용 조회 성공", boardService.selectBoardDetail(boardCode)));
	}
	
	/* 5. 새 공지사항 등록 */

	
	
	/* 6. 등록된 공지사항 수정 */
	
	/* 7. 등록된 공지사항 삭제 */
	
	/* 8. 공지사항 조회수 증가 */
	
	
	
	
}
