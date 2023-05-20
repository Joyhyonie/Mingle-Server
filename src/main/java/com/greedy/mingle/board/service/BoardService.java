package com.greedy.mingle.board.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.mingle.board.dto.BoardDTO;
import com.greedy.mingle.board.entity.Board;
import com.greedy.mingle.board.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	private final ModelMapper modelMapper;
	
	public BoardService(BoardRepository boardRepository, ModelMapper modelMapper) {
		this.boardRepository = boardRepository;
		this.modelMapper = modelMapper;
	}
	
	/* 1. 최신 공지사항 7개 조회 */
	public List<BoardDTO> selectBoardPreview(String boardStatus) {
		
		List<Board> boardList = boardRepository.findTop7ByBoardStatusOrderByBoardWriteDateDesc(boardStatus);
		
		log.info("[BoardService] : boardList {}", boardList);
		
		List<BoardDTO> boardDTOList = boardList.stream()
		            .map(board -> modelMapper.map(board, BoardDTO.class))
		            .collect(Collectors.toList());
		
		return boardDTOList;
		
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
