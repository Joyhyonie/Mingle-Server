package com.greedy.mingle.certi.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.certi.dto.CertiDocDTO;
import com.greedy.mingle.certi.service.CertiDocService;
import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.common.paging.Pagenation;
import com.greedy.mingle.common.paging.PagingButtonInfo;
import com.greedy.mingle.common.paging.ResponseDTOWithPaging;

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
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDTO> updateCertiDoc(@ModelAttribute CertiDocDTO certiDocDto){
		
		certiDocService.updateCertiDoc(certiDocDto);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "수정 성공"));
	}

}
