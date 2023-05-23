package com.greedy.mingle.employee.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.common.paging.Pagenation;
import com.greedy.mingle.common.paging.PagingButtonInfo;
import com.greedy.mingle.common.paging.ResponseDTOWithPaging;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.employee.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/employee" )
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/* 1. 교직원 목록 조회 - 페이징 */
	@GetMapping("/employees")
	public ResponseEntity<ResponseDTO> selectEmployeeList(@RequestParam(name = "page", defaultValue = "1") int page) {

		log.info("[EmployeeController] : selectEmployeeList start ==================================== ");
		log.info("[EmployeeController] : page : {}", page);

		Page<EmployeeDTO> employeeDtoList = employeeService.selectEmployeeList(page);

		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(employeeDtoList);

		log.info("[EmployeeController] : pageInfo : {}", pageInfo);

		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(employeeDtoList.getContent());

		log.info("[EmployeeController] : selectEmployeeList end ==================================== ");

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));

	}

	/* 2. 교직원 목록 조회 - 소속 기준, 페이징 */
	@GetMapping("/employees/department/{deptCode}")
	public ResponseEntity<ResponseDTO> selectEmployeeListByDepartment(
			@RequestParam(name = "page", defaultValue = "1") int page, @PathVariable Long deptCode) {

		log.info("[EmployeeController] : selectEmployeeListByDepartment start ==================================== ");
		log.info("[EmployeeController] : page : {}", page);

		Page<EmployeeDTO> employeeDtoList = employeeService.selectEmployeeListByDepartment(page, deptCode);

		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(employeeDtoList);

		log.info("[ProductController] : pageInfo : {}", pageInfo);

		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(employeeDtoList.getContent());

		log.info("[EmployeeController] : selectEmployeeListByDepartment end ==================================== ");

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	/* 2-1. selectbox - 교수만 조회 */
	
	/* 2-2. selectbox - 교직원만 조회 */

	/* 3. 교직원 목록 조회 - 교직원명 검색 기준, 페이징 */
	@GetMapping("/employees/search")
	public ResponseEntity<ResponseDTO> selectEmployeeListByEmpName(
			@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "search") String empName) {

		log.info("[EmployeeController] : selectEmployeeListByEmpName start ==================================== ");
		log.info("[EmployeeController] : page : {}", page);
		log.info("[EmployeeController] : employeeName : {}", empName);

		Page<EmployeeDTO> employeeDtoList = employeeService.selectEmployeeListByEmpName(page, empName);

		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(employeeDtoList);

		log.info("[EmployeeController] : pageInfo : {}", pageInfo);

		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(employeeDtoList.getContent());

		log.info("[EmployeeController] : selectEmployeeListByEmpName end ==================================== ");

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));

	}

	/* 4. 교직원 상세 조회 - empCode로 교직원 1명 조회 */
	@GetMapping("employees/{empCode}")
	public ResponseEntity<ResponseDTO> selectEmployeeDetail(@PathVariable Long empCode) {

		return ResponseEntity.ok()
				.body(new ResponseDTO(HttpStatus.OK, "조회 성공", employeeService.selectEmployee(empCode)));
	}

	/* 5. 교직원 신규 등록 */
	@PostMapping("/insert")
	public ResponseEntity<ResponseDTO> insertEmployee(@ModelAttribute EmployeeDTO employeeDto) {

		employeeService.insertEmployee(employeeDto);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "등록 성공"));
	}

	/* 6. 교직원 정보 수정 */
	@PutMapping("/modify")
	public ResponseEntity<ResponseDTO> updateEmployee(@ModelAttribute EmployeeDTO employeeDto) {

		employeeService.updateEmployee(employeeDto);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "등록 성공"));
	}

	/*
	 * 7. 교직원 정보 삭제
	 * 
	 * @DeleteMapping("/delete/{empCode}") public ResponseEntity<ResponseDTO>
	 * deleteEmployee(@PathVariable String empCode){
	 * 
	 * employeeService.deleteEmployee(empCode);
	 * 
	 * return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "삭제 성공")); }
	 */

	/* 8. 조직도 교직원 조회 - 스크롤 */

	/* 9. 조직도 교직원 조회 - 소속 기준 */

	/* 자신의 마이페이지 조회 */
	@GetMapping("/myemployees")
	public ResponseEntity<ResponseDTO> selectMyInfo(@AuthenticationPrincipal EmployeeDTO employee) {
		
		log.info("[확인용] {}", employee);
		
		return ResponseEntity.ok()
				.body(new ResponseDTO(HttpStatus.OK, "조회 완료", employeeService.selectInfo(employee.getEmpCode())));
	}

	/* 마이페이지 수정 */
	@PatchMapping(value = "/putmypage")
	public ResponseEntity<ResponseDTO> updateEmp(@RequestParam("myPageImage") MultipartFile myPageImage, @AuthenticationPrincipal EmployeeDTO employeeDTO) {
	    employeeDTO.setMyPageImage(myPageImage);
		  log.info("[확인용] {}", employeeDTO);
	    employeeService.updateEmp(employeeDTO);
	
	    
	    
	    return ResponseEntity.ok()
	            .body(new ResponseDTO(HttpStatus.OK, "정보 수정 성공"));
	}

}
