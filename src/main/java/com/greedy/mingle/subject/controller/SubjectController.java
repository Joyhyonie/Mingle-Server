package com.greedy.mingle.subject.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.common.paging.Pagenation;
import com.greedy.mingle.common.paging.PagingButtonInfo;
import com.greedy.mingle.common.paging.ResponseDTOWithPaging;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.subject.dto.SubjectDTO;
import com.greedy.mingle.subject.service.SubjectService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/subject")
/* 과목 관리 컨트롤러 */
public class SubjectController {
	
	private final SubjectService subjectService;
	
	public SubjectController(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
	
	@GetMapping("/list")
	public ResponseEntity<ResponseDTO> selectSubjectList(@RequestParam(name="page", defaultValue="1") int page){
		
		Page<SubjectDTO> subjectDtoList = subjectService.selectSubjectList(page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(subjectDtoList);
		
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(subjectDtoList.getContent());
		
		log.info("[SubjectController : responseDtoWithPaging : {}",responseDtoWithPaging);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공",responseDtoWithPaging));
	}
	
	@GetMapping("/department/{deptCode}")
	public ResponseEntity<ResponseDTO> selectSubjectListByDepartment(
			@RequestParam(name="page", defaultValue="1") int page, @PathVariable Long deptCode){
		
		Page<SubjectDTO> subjectDtoList = subjectService.selectSubjectListByDepartment(page, deptCode);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(subjectDtoList);
		
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(subjectDtoList.getContent());
		
		log.info("[SubjectController : responseDtoWithPaging : {}",responseDtoWithPaging);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	@GetMapping("/search")
	public ResponseEntity<ResponseDTO> selectSubjectListBySearchName(
			@RequestParam(name="page", defaultValue="1") int page,	
			@RequestParam(name="condition")String condition,
			@RequestParam(name="search") String name){
		
		Page<SubjectDTO> employeeDTOList = subjectService.selectEmployeeListByDeptName(page, condition, name);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(employeeDTOList);

		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(employeeDTOList.getContent());
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	@PutMapping("/modify")
	public ResponseEntity<ResponseDTO> updateSubject(@ModelAttribute SubjectDTO subjectDto){
		
		subjectService.updateSubject(subjectDto);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "수정 성공"));
	}
	
	@PostMapping("/insert")
	public ResponseEntity<ResponseDTO> insertSubject(@ModelAttribute SubjectDTO subjectDto){
		
		subjectService.insertSubject(subjectDto);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "등록 성공"));
	}
	
	
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDTO> deleteSubjects(@RequestBody List<Long> sbjCodes) {
	    try {
	        for (Long sbjCode : sbjCodes) {
	            subjectService.deleteSubject(sbjCode);
	        }
	        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "삭제 성공"));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "삭제 실패"));
	    }
	}
	
	

}
