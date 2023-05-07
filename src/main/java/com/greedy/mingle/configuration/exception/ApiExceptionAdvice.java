package com.greedy.mingle.configuration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.greedy.mingle.exception.dto.ApiExceptionDTO;

@RestControllerAdvice
public class ApiExceptionAdvice {

	@ExceptionHandler({UserNotFoundException.class, IllegalArgumentException.class})
	public ResponseEntity<ApiExceptionDTO> exceptionHandler(Exception e) {
		
		/* 같은 Http 상태코드를 가지는 등의 비슷한 Exception은 한꺼번에 묶어 처리 가능 (매개변수는 최상위 타입인 Exception으로 변경) */
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST) 									// 상태 설정
				.body(new ApiExceptionDTO(HttpStatus.BAD_REQUEST, e.getMessage()));	// 메세지 설정
		
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiExceptionDTO> exceptionHandler(RuntimeException e) {
		
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR) 										// 상태 설정
				.body(new ApiExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));	// 메세지 설정
		
	}
	
}
