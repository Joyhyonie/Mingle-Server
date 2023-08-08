package com.greedy.mingle.employee.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.greedy.mingle.subject.dto.SubjectDTO;

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

		Page<EmployeeDTO> employeeDtoList = employeeService.selectEmployeeList(page);

		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(employeeDtoList);

		log.info("[EmployeeController] : pageInfo : {}", pageInfo);

		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(employeeDtoList.getContent());

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));

	}

	/* 2. 교직원 목록 조회 - 소속 기준, 페이징 */
	@GetMapping("/employees/department/{deptCode}")
	public ResponseEntity<ResponseDTO> selectEmployeeListByDepartment(
			@RequestParam(name = "page", defaultValue = "1") int page, @PathVariable Long deptCode) {

		Page<EmployeeDTO> employeeDtoList = employeeService.selectEmployeeListByDepartment(page, deptCode);

		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(employeeDtoList);

		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(employeeDtoList.getContent());

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}

	/* 3. 교직원 목록 조회 - 교직원명 검색 기준, 페이징 */
	@GetMapping("/search")
	public ResponseEntity<ResponseDTO> selectEmployeeListBySearchName(
			@RequestParam(name="page", defaultValue="1") int page,	
			@RequestParam(name="condition")String condition,
			@RequestParam(name="search") String name){
		
		Page<EmployeeDTO> employeeDTOList = employeeService.selectEmployeeListByDeptName(page, condition, name);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(employeeDTOList);

		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(employeeDTOList.getContent());
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
	public ResponseEntity<ResponseDTO> updateEmployee(@ModelAttribute EmployeeDTO employeeDTO, 
			@AuthenticationPrincipal EmployeeDTO loggedInEmployee) {
	    
	    log.info("교직원 정보 수정 : ", employeeDTO);
		employeeService.updateEmployee(employeeDTO);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "수정 성공"));
	}


	 /* 7. 교직원 정보 삭제 */
	 
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDTO> deleteEmployees(@RequestBody List<Long> empCodes) {
	    try {
	        for (Long empCode : empCodes) {
	            employeeService.deleteEmployee(empCode);
	        }
	        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "삭제 성공"));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "삭제 실패"));
	    }
	}

	/* 10. 자신의 마이페이지 조회 */
	@GetMapping("/myemployees")
	public ResponseEntity<ResponseDTO> selectMyInfo(@AuthenticationPrincipal EmployeeDTO employee) {
		
		log.info("[확인용] {}", employee);
		
		return ResponseEntity.ok()
				.body(new ResponseDTO(HttpStatus.OK, "조회 완료", employeeService.selectInfo(employee.getEmpCode())));
	}

	/* 11. 마이페이지 수정 */
	@PatchMapping("/putmypage")
	public ResponseEntity<ResponseDTO> updateEmp(
	        @RequestParam(value = "myPageImage", required = false) MultipartFile myPageImage,
	        @ModelAttribute EmployeeDTO employeeDTO,
	        @AuthenticationPrincipal EmployeeDTO loggedInEmployee) {
	  
		employeeDTO.setMyPageImage(myPageImage);
		employeeDTO.setEmpCode(loggedInEmployee.getEmpCode()); // 로그인한 사용자의 코드를 설정
	    employeeDTO.setEmpId(loggedInEmployee.getEmpId()); // 로그인한 사용자의 아이디를 설정
		
	    employeeService.updateEmp(employeeDTO);

	    return ResponseEntity.ok()
	            .body(new ResponseDTO(HttpStatus.OK, "정보 수정 성공"));
	}
	
	
	/* 12. 조직도 목록 조회 - 페이징 */
	@GetMapping("/organization")
	public ResponseEntity<ResponseDTO> selectOrganizationList(@RequestParam(name = "page", defaultValue = "1") int page) {

		log.info("[EmployeeController] : selectOrganizationList start ==================================== ");
		log.info("[EmployeeController] : page : {}", page);

		Page<EmployeeDTO> employeeDtoList = employeeService.selectOrganizationList(page);

		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(employeeDtoList);

		log.info("[EmployeeController] : pageInfo : {}", pageInfo);

		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(employeeDtoList.getContent());

		log.info("[EmployeeController] : selectOrganizationList end ==================================== ");

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));

	}
	
	/* 13. 조직도 목록 조회 - 교직원명 검색 기준, 페이징 */
	@GetMapping("/organization/search")
	public ResponseEntity<ResponseDTO> selectOrgListByEmpName(
			@RequestParam(name = "page", defaultValue = "1") int page, 
			@RequestParam(name = "search") String name,
			@RequestParam(name = "condition") String condition) {

		log.info("[EmployeeController] : selectOrgListByEmpName start ==================================== ");
		log.info("[EmployeeController] : page : {}", page);
		log.info("[EmployeeController] : employeeName : {}", name);

		Page<EmployeeDTO> employeeDtoList = employeeService.selectEmployeeListByDeptName(page, condition, name);

		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(employeeDtoList);

		log.info("[EmployeeController] : pageInfo : {}", pageInfo);

		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(employeeDtoList.getContent());

		log.info("[EmployeeController] : selectOrgListByEmpName end ==================================== ");

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));

	}
	

}
