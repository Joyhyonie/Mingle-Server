package com.greedy.mingle.employee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.employee.dto.MailDTO;
import com.greedy.mingle.employee.service.SendEmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")

public class AuthController {

	private final AuthService authService;
	private final SendEmailService sendEmailService;

	public AuthController(AuthService authService,SendEmailService sendEmailService) {
		this.authService = authService;
		this.sendEmailService = sendEmailService;
			
	}

	
	/*
	 * //1. 회원 가입
	 * 
	 * @PostMapping("/signup") public ResponseEntity<ResponseDTO>
	 * signup(@RequestBody EmployeeDto employeeDto){
	 * 
	 * authService.signup(employeeDto);
	 * 
	 * return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회원 가입 완료"));
	 * 
	 * }
	 */
	 
	
		
		  // 2. 로그인
		  
		  @PostMapping("/login") 
		  public ResponseEntity<ResponseDTO> login(@RequestBody
		  EmployeeDTO employeeDto) {
		  
		  
		  
		  return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "로그인 완료",
		  authService.login(employeeDto)));
		  
		  }
		 
	 
		  /* 아이디 찾기 */
		   @PostMapping("/idsearch")
		   public ResponseEntity<ResponseDTO> idSearch(@RequestBody EmployeeDTO employeeDTO) {

		      return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "아이디 찾기 성공", authService.idSearch(employeeDTO)));
		   }


		   /* 비밀번호 찾기(이메일 발송) */
		   @PostMapping("/sendemail")
		   public ResponseEntity<ResponseDTO> sendEmail(@RequestBody EmployeeDTO employeeDTO) {
		      
		      /* 1. 전달받은 아이디를 통해 db에서 정보부터 가져오기 */
		      EmployeeDTO getemployeeDTO = authService.findById(employeeDTO);
		      
		      /* 2. 가져온 정보를 이용하여 이메일 발송 */
		      if (getemployeeDTO != null) {
		         
		         log.info("[AuthController] getemployeeDTO : {}", getemployeeDTO);
		         
		         MailDTO dto = sendEmailService.createMailAndChangePassword(getemployeeDTO.getEmpId(),
		                getemployeeDTO.getEmpEmail(), getemployeeDTO.getEmpName());
		         
		         
		         sendEmailService.mailSend(dto);

		         return ResponseEntity
		               .ok()
		               .body(new ResponseDTO(HttpStatus.OK, "이메일이 전송되었습니다."));

		      } else {
		         return ResponseEntity.badRequest().body(new ResponseDTO(HttpStatus.BAD_REQUEST, "이메일이 일치하지 않습니다."));
		      }
		   }


}
