package com.greedy.mingle.board.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	public List<BoardDTO> selectBoardPreview() {
		
		List<Board> boardList = boardRepository.findTop7ByBoardStatusOrderByBoardWriteDateDesc("Y");
		
		log.info("[BoardService] boardList : {}", boardList);
		
		List<BoardDTO> boardDTOList = boardList.stream()
		            .map(board -> modelMapper.map(board, BoardDTO.class))
		            .collect(Collectors.toList());
		
		return boardDTOList;
		
	}

	/* 2. 전체 공지사항 조회(페이징) */
	public Page<BoardDTO> selectBoardList(int page) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("boardCode").descending()); // PageRequest.of(몇 번째 페이지?, 몇 개씩?, 정렬기준)
		
		Page<Board> boardList = boardRepository.findByBoardStatus("Y", pageable);
		Page<BoardDTO> boardDTOList = boardList.map(board -> modelMapper.map(board, BoardDTO.class));

		return boardDTOList;
	}

	/* 3. 분류 및 검색기준별 공지사항 조회(페이징) */
	public Page<BoardDTO> selectSearchedBoard(int page, String type, String condition, String word) {
		
		log.info("[BoardService] type : {}", type);
		log.info("[BoardService] condition : {}", condition);
		log.info("[BoardService] word : {}", word);

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("boardCode").descending()); // PageRequest.of(몇 번째 페이지?, 몇 개씩?, 정렬기준)
		
		// condition별 메소드 호출
		Page<Board> boardList;
		
		switch(condition) {
			case "title" :  boardList = boardRepository.findByBoardTitle(type, word, pageable); break;
			case "content" : boardList = boardRepository.findByBoardContent(type, word, pageable); break;
			case "writer" : boardList = boardRepository.findByWriter(type, word, pageable); break;
			default: throw new IllegalArgumentException("일치하는 검색 기준이 없습니다 🥲 condition : " + condition);
		}
		
		Page<BoardDTO> boardDTOList = boardList.map(board -> modelMapper.map(board, BoardDTO.class));

		return boardDTOList;
		
	}

	/* 4. 공지 상세 내용 조회 */
	public BoardDTO selectBoardDetail(Long boardCode) {
		
		Board board = boardRepository.findByBoardCode(boardCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 공지사항이 없어유🤕 boardCode=" + boardCode)); 
		
		BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);
		
		return boardDTO;
	}
	
	
	
	
	/* 5. 새 공지사항 등록 */
	
	/* 6. 등록된 공지사항 수정 */
	
	/* 7. 등록된 공지사항 삭제 */
	
	/* 8. 공지사항 조회수 증가 */
	
}
