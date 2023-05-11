package com.greedy.mingle.employee.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.employee.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.common.paging.Pagenation;
import com.greedy.mingle.common.paging.PagingButtonInfo;
import com.greedy.mingle.common.paging.ResponseDTOWithPaging;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
	
	private final EmployeeService employeeService;
	
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	/* 1. 교직원 목록 조회 - 페이징 */
	@GetMapping("/employees")
	public ResponseEntity<ResponseDTO> selectEmployeeList(@RequestParam(name="page", defaultValue="1") int page) {
		
		log.info("[EmployeeController] : selectEmployeeList start ============================== ");
		log.info("[EmployeeController] : page : {}", page);
		
		Page<EmployeeDTO> employeeDtoList = employeeService.selectEmployeeList(page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(employeeDtoList);
		
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(employeeDtoList.getContent());
		
		log.info("[EmployeeController] : selectEmployeeList end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));

		
	}
	
	/* 2. 교직원 목록 조회 - 소속 기준, 페이징 */
	
	/* 3. 교직원 목록 조회 - 교직원명 검색 기준, 페이징 */
	
	/* 4. 교직원 상세 조회 - empCode로 교직원 1명 조회 */
	
	/* 5. 교직원 신규 등록 */
	
	/* 6. 교직원 정보 수정 */
	
	/* 7. 조직도 교직원 조회 - 스크롤 */
	
	/* 8. 조직도 교직원 조회 - 소속 기준 */
	
	/* 9. 조직도 교직원 조회 - 교직원명 검색 기준 */
	
	
}
