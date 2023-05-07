package com.greedy.mingle.exception.dto;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiExceptionDTO {

	private int state;
	private String message;
	
	public ApiExceptionDTO(HttpStatus status, String message) {
		this.state = status.value();
		this.message = message;
	}
}
