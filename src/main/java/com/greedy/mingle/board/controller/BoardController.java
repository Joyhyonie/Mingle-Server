package com.greedy.mingle.board.controller;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.board.dto.BoardDTO;
import com.greedy.mingle.board.service.BoardService;
import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.common.paging.Pagenation;
import com.greedy.mingle.common.paging.PagingButtonInfo;
import com.greedy.mingle.common.paging.ResponseDTOWithPaging;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.notification.dto.NotificationDTO;
import com.greedy.mingle.notification.dto.NotificationTypeDTO;
import com.greedy.mingle.notification.service.NotificationService;

@RestController
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	private final NotificationService notiService;
	
	public BoardController(BoardService boardService, NotificationService notiService) {
		this.boardService = boardService;
		this.notiService = notiService;
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
				.body(new ResponseDTO(HttpStatus.OK, "분류 및 검색기준별 공지사항 조회 성공", responseDTOWithPaging));
	}
	
	/* 4. 공지 상세 내용 조회 */
	@GetMapping("/{boardCode}")
	public ResponseEntity<ResponseDTO> selectBoardDetail(@PathVariable Long boardCode) {
	
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "공지 상세 내용 조회 성공", boardService.selectBoardDetail(boardCode)));
	}
	
	/* 5. 새 공지사항 등록 */
	@PostMapping("/regist")
	public ResponseEntity<ResponseDTO> registBoard(@RequestBody BoardDTO boardDTO, @AuthenticationPrincipal EmployeeDTO writer) {
		
		boardDTO.setWriter(writer);
		boardService.registBoard(boardDTO);
		
		/* 공지사항이 등록될 때, 알림 테이블에 insert되는 동시에 모든 교직원에게 실시간 알림 전송 */
		// 공지사항의 알림 유효시간은 3일
		Date now = new Date();
		Date threeDaysAfter = new Date(now.getTime() + (1000 * 60 * 60 * 24 * 3));
		// NotiType 설정
		NotificationTypeDTO notiTypeDTO = new NotificationTypeDTO();
		notiTypeDTO.setNotiTypeCode((long) 40001);
		notiTypeDTO.setNotiTitle("새로운 공지사항이 등록되었습니다.");
		// Noti 설정
		NotificationDTO notiDTO = new NotificationDTO();
		notiDTO.setNotiContent(boardDTO.getBoardTitle());
		notiDTO.setNotiEndDate(threeDaysAfter);
		notiDTO.setNotiType(notiTypeDTO);
		
    	notiService.addNoti(notiDTO);
		notiService.notifyCommonNoti(notiDTO);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "새 공지사항 등록 성공"));
		
	}
	
	/* 6. 등록된 공지사항 수정 */
	@PutMapping("/modify")
	public ResponseEntity<ResponseDTO> modifyBoard(@RequestBody BoardDTO boardDTO) {
		
		boardService.modifyBoard(boardDTO);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "등록된 공지사항 수정 성공"));
		
	}
	
	/* 7. 등록된 공지사항 삭제 */
	@PatchMapping("/remove/{boardCode}")
	public ResponseEntity<ResponseDTO> removeBoard(@PathVariable Long boardCode) {
		
		boardService.removeBoard(boardCode);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "등록된 공지사항 삭제 성공"));
		
	}
	
	
	/* 8. 공지사항 조회수 증가 */
	@PatchMapping("/count-up/{boardCode}")
	public ResponseEntity<ResponseDTO> countUpBoard(@PathVariable Long boardCode) {
		
		boardService.countUpBoard(boardCode);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK, "공지사항 조회수 증가 성공"));
		
	}
	
	
}
