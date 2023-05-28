package com.greedy.mingle.employee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.employee.dto.MailDTO;
import com.greedy.mingle.employee.dto.PasswordChangeDTO;
import com.greedy.mingle.employee.service.SendEmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")

public class AuthController {

	private final AuthService authService;
	private final SendEmailService sendEmailService;
	private final PasswordEncoder passwordEncoder;
	
	
	public AuthController(AuthService authService,SendEmailService sendEmailService, PasswordEncoder passwordEncoder) {
		this.authService = authService;
		this.sendEmailService = sendEmailService;
		this.passwordEncoder = passwordEncoder;
			
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
		  
			  log.info("hi 내가 먼저야!  : {}",  employeeDto);
		  
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
		               .body(new ResponseDTO(HttpStatus.OK, "임시 비밀번호가 발급되었습니다."));

		      } else {
		         return ResponseEntity.badRequest().body(new ResponseDTO(HttpStatus.BAD_REQUEST, "이메일이 일치하지 않습니다."));
		      }
		   }

		   /* 비밀번호 변경 */
		   @PostMapping("/pwdChange")
		   public ResponseEntity<ResponseDTO> pwdChange(@AuthenticationPrincipal EmployeeDTO employee, @RequestBody PasswordChangeDTO passwordChangeDTO) {
		       EmployeeDTO existingEmployee = authService.findByEmpId(employee.getEmpId());

		       // Check if existing password is different from new password
		       if (!passwordChangeDTO.getExistingPassword().equals(passwordChangeDTO.getNewPassword())) {
		           // Check if new password matches confirm password
		           if (passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getConfirmPassword())) {
		               // Check if existing password is correct
		               if (passwordEncoder.matches(passwordChangeDTO.getExistingPassword(), existingEmployee.getEmpPwd())) {
		                   // Check if new password meets the required criteria
		                   if (isPasswordValid(passwordChangeDTO.getNewPassword())) {
		                       // Update password
		                       String encodedNewPassword = passwordEncoder.encode(passwordChangeDTO.getNewPassword());
		                       existingEmployee.setEmpPwd(encodedNewPassword);
		                       authService.updateEmployee(existingEmployee); // Update in the database

		                       return ResponseEntity.ok()
		                               .body(new ResponseDTO(HttpStatus.OK, "비밀번호가 변경되었습니다."));
		                   } else {
		                       return ResponseEntity.badRequest()
		                               .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "비밀번호는 영문과 특수문자, 숫자 2가지 이상 조합하여 10~16자리로 입력해주세요."));
		                   }
		               } else {
		                   return ResponseEntity.badRequest()
		                           .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "기존 비밀번호가 일치하지 않습니다."));
		               }
		           } else {
		               return ResponseEntity.badRequest()
		                       .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다."));
		           }
		       } else {
		           return ResponseEntity.badRequest()
		                   .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "입력된 비밀번호와 새 비밀번호가 동일합니다."));
		       }
		   }
		   
		// 비밀번호 유효성 검사 메소드
		   private boolean isPasswordValid(String password) {
		       // 영문, 특수문자, 숫자 2가지 이상 조합하여 10~16자리

		       // 비밀번호 길이 확인 (10~16자리)
		       if (password.length() < 10 || password.length() > 16) {
		           return false;
		       }

		       // 영문, 특수문자, 숫자 각각의 포함 여부 확인
		       boolean hasUpperCase = false; // 대문자 포함 여부
		       boolean hasLowerCase = false; // 소문자 포함 여부
		       boolean hasDigit = false; // 숫자 포함 여부
		       boolean hasSpecialCharacter = false; // 특수문자 포함 여부

		       for (char c : password.toCharArray()) {
		           if (Character.isUpperCase(c)) {
		               hasUpperCase = true;
		           } else if (Character.isLowerCase(c)) {
		               hasLowerCase = true;
		           } else if (Character.isDigit(c)) {
		               hasDigit = true;
		           } else if (isSpecialCharacter(c)) {
		               hasSpecialCharacter = true;
		           }
		       }

		       // 영문, 특수문자, 숫자 중 최소 2가지가 포함되어 있는지 확인
		       int count = 0;
		       if (hasUpperCase) {
		           count++;
		       }
		       if (hasLowerCase) {
		           count++;
		       }
		       if (hasDigit) {
		           count++;
		       }
		       if (hasSpecialCharacter) {
		           count++;
		       }

		       if (count < 2) {
		           return false;
		       }

		       return true;
		   }

		 
		// 특수문자인지 확인하는 메소드
		   private boolean isSpecialCharacter(char c) {
		       // 특수문자 패턴에 따라 확인하여 true 또는 false 반환
		       // 예: !@#$%^&*()
		       String specialCharacters = "!@#$%^&*()";

		       return specialCharacters.contains(String.valueOf(c));
		   }
}