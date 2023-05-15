package com.greedy.mingle.employee.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greedy.mingle.exception.dto.ApiExceptionDTO;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;
	
	public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
		
		this.objectMapper = objectMapper;
	}
	/* 유효한 자격 증명을 제공하지 않고 접근하려 하는 경우 인증 실패이므로 401오류를 반환한다.*/
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		ApiExceptionDTO errorResponse = new ApiExceptionDTO(HttpStatus.UNAUTHORIZED, "인증 되지 않은 유저 입니다.");
		
		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
	}

}
