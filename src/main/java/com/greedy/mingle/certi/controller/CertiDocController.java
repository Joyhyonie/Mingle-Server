package com.greedy.mingle.certi.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.attendance.dto.LeaveDocDTO;
import com.greedy.mingle.certi.dto.CertiDocDTO;
import com.greedy.mingle.certi.service.CertiDocService;
import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.common.paging.Pagenation;
import com.greedy.mingle.common.paging.PagingButtonInfo;
import com.greedy.mingle.common.paging.ResponseDTOWithPaging;
import com.greedy.mingle.employee.dto.EmployeeDTO;

@RestController
@RequestMapping("/certi")
public class CertiDocController {
	
	private final CertiDocService certiDocService;
	
	public CertiDocController(CertiDocService certiDocService) {
		this.certiDocService = certiDocService;
	}
	
	@GetMapping("/list")
	public ResponseEntity<ResponseDTO> getCertiList(@RequestParam(name="page",defaultValue="1") int page){
		
		Page<CertiDocDTO> certiDocDtoList = certiDocService.getCertiList(page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(certiDocDtoList);
		
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(certiDocDtoList.getContent());
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
		
	}
	
	@GetMapping("/search")
	public ResponseEntity<ResponseDTO> selectCertiDocSearchName(
			@RequestParam(name="page", defaultValue="1") int page,	
			@RequestParam(name="condition")String condition,
			@RequestParam(name="search") String name){
		
		Page<CertiDocDTO> certiDocDTOList = certiDocService.selectCertiDocSearchName(page, condition, name);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(certiDocDTOList);

		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(certiDocDTOList.getContent());
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	@GetMapping("/myCertiDocSearch")
	public ResponseEntity<ResponseDTO> selectMyCertiDocSearchName(
			@RequestParam(name="page", defaultValue="1") int page,	
			@RequestParam(name="condition")String condition,
			@RequestParam(name="search") String name,
			@AuthenticationPrincipal EmployeeDTO employee){
		
		Page<CertiDocDTO> certiDocDTOList = certiDocService.selectMyCertiDocSearchName(page, condition, name,employee.getEmpCode());
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(certiDocDTOList);

		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(certiDocDTOList.getContent());
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	@PatchMapping("/update/{certiDocCOde}")
	public ResponseEntity<ResponseDTO> updateCertiDoc(@PathVariable Long certiDocCOde,@RequestBody CertiDocDTO certiDocDto,@AuthenticationPrincipal EmployeeDTO employee){
		
		certiDocDto.setAccepter(employee);
		certiDocService.updateCertiDoc(certiDocCOde, certiDocDto);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "수정 성공"));
	}
	
	@GetMapping("/myCerti")
	public ResponseEntity<ResponseDTO> selectMemberCerti(@RequestParam(name="page",defaultValue="1") int page,
			@AuthenticationPrincipal EmployeeDTO employee){
				
		Page<CertiDocDTO> certiDocDTO = certiDocService.selectEmployeeCerti(page,employee.getEmpCode());
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(certiDocDTO);
		
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(certiDocDTO.getContent());
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"조회 성공",responseDtoWithPaging));
	}
	
	@GetMapping("/myCerti/{certiDocCode}")
	public ResponseEntity<ResponseDTO> detailCerti(@PathVariable Long certiDocCode){
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"조회 성공",certiDocService.selectCertiDoc(certiDocCode)));
	}
	
	@PostMapping("/regist")
	public ResponseEntity<ResponseDTO> registCerti(@AuthenticationPrincipal EmployeeDTO employee,
			@ModelAttribute CertiDocDTO certiDocDTO){
		
		certiDocDTO.setApplyer(employee);
		certiDocService.registCertiDoc(certiDocDTO);		
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"등록 성공"));
	}

}
