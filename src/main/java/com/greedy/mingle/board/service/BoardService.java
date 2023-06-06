package com.greedy.mingle.board.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		
		List<Board> boardList = boardRepository.findTop7ByBoardStatusOrderByBoardCodeDesc("Y");
		
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

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("boardCode").descending()); // PageRequest.of(몇 번째 페이지?, 몇 개씩?, 정렬기준)
		
		// condition별 메소드 호출
		Page<Board> boardList;
		
		switch(condition) {
			case "title" :  boardList = boardRepository.findByBoardTitle(type, word, pageable); break;
			case "content" : boardList = boardRepository.findByBoardContent(type, word, pageable); break;
			case "writer" : boardList = boardRepository.findByWriter(type, word, pageable); break;
			default : boardList = boardRepository.findByBoardTypeAndBoardStatus(type, "Y", pageable);
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
	@Transactional
	public void registBoard(BoardDTO boardDTO) {
		
		boardRepository.save(modelMapper.map(boardDTO, Board.class));
		
	}

	/* 6. 등록된 공지사항 수정 */
	@Transactional
	public void modifyBoard(BoardDTO boardDTO) {
		
		Board board = boardRepository.findByBoardCode(boardDTO.getBoardCode())
				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 공지사항이 없어유🤕 boardCode=" + boardDTO.getBoardCode())); 
		
		// 공지사항 수정용 메소드(boardUpdate())를 통한 수정
		board.boardUpdate(boardDTO.getBoardType(), boardDTO.getBoardTitle(), boardDTO.getBoardContent());
		
	}

	/* 7. 등록된 공지사항 삭제 */
	@Transactional
	public void removeBoard(Long boardCode) {
		
		Board board = boardRepository.findByBoardCode(boardCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 공지사항이 없어유🤕 boardCode=" + boardCode)); 
		
		board.setBoardStatus("N");
		boardRepository.save(board);
		
	}

	/* 8. 공지사항 조회수 증가 */
	@Transactional
	public void countUpBoard(Long boardCode) {
		
		Board board = boardRepository.findByBoardCode(boardCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 공지사항이 없어유🤕 boardCode=" + boardCode)); 
		
		int count = board.getBoardCount();
		board.setBoardCount(count + 1);
		boardRepository.save(board);
		
	}
	
	
	
	
}
