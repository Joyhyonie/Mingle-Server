/*
 * package com.greedy.mingle.employee.controller;
 * 
 * import org.springframework.http.HttpStatus; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.greedy.mingle.common.ResponseDTO; import
 * com.greedy.mingle.employee.dto.EmployeeDTO;
 * 
 * @RestController
 * 
 * @RequestMapping("/auth")
 * 
 * public class AuthController {
 * 
 * private final AuthService authService;
 * 
 * public AuthController(AuthService authService) { this.authService =
 * authService;
 * 
 * }
 * 
 * 
 * 
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
 * 
 * 
 * 
 * 
 * // 2. 로그인
 * 
 * @PostMapping("/login") public ResponseEntity<ResponseDTO> login(@RequestBody
 * EmployeeDTO employeeDto) {
 * 
 * 
 * 
 * return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "로그인 완료",
 * authService.login(employeeDto)));
 * 
 * }
 * 
 * 
 * 
 * }
 */