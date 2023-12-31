package com.greedy.mingle.lecture.controller;



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

import com.greedy.mingle.common.ResponseDTO;
import com.greedy.mingle.common.paging.Pagenation;
import com.greedy.mingle.common.paging.PagingButtonInfo;
import com.greedy.mingle.common.paging.ResponseDTOWithPaging;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.lecture.dto.LectureOfficerDTO;
import com.greedy.mingle.lecture.service.LectureService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/lecture")
public class LectureController {
	
	private final LectureService lectureService;
	
	public LectureController(LectureService lectureService) {
		this.lectureService=lectureService;
	}
	
	/*행정 직원 등록 페이지(학과 목록 조회)*/
	@GetMapping("departments")
	public ResponseEntity<ResponseDTO> getDeptInfo(){
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", lectureService.getDeptInfo()));

		
	}
	/*행정 직원의 강의 등록을 위한 데이터 조회(교수, 과목 목록 조회)*/
	@GetMapping("professorsAndLectures/{deptCode}")
	public ResponseEntity<ResponseDTO> getProfessorsAndLecturesInfo(@PathVariable Long deptCode ){
		
	
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", lectureService.getProfessorsAndLecturesInfo(deptCode)));

		
	}
	
	/*행정직원의 강의등록(입력)*/
	@PostMapping("/officerregistration")
	public ResponseEntity<ResponseDTO> insertLecture (@RequestBody LectureOfficerDTO lectureOfficerDTO){
		
		
		lectureService.insertLecture(lectureOfficerDTO);
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "등록 성공"));
		
	}
	
	/*행정직원의 출석,성적체크를 위한 강의리스트(강의 개설 완료) */
	@GetMapping("/adminLectureList")
	public ResponseEntity<ResponseDTO>getAdminLectureList(@RequestParam(name = "page", defaultValue = "1") int page){
		
		log.info("[LectureController] : selectLectureList start ==================================== ");
		log.info("[LectureController] : page : {}", page);
		
		Page<LectureOfficerDTO> lectureDtoList = lectureService.lectureList(page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(lectureDtoList);
		log.info("[LectureController] : pageInfo : {}", pageInfo);
		
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(lectureDtoList.getContent());

		log.info("[LectureController] : selectEmployeeList end ==================================== ");
		
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	/*행정직원의 강의개설 리스트*/
	@GetMapping("/adminOpenLectureList")
	public ResponseEntity<ResponseDTO>getAdminOpenLectureList(@RequestParam(name = "page", defaultValue = "1") int page){
		
		log.info("[LectureController] : selectLectureList start ==================================== ");
		log.info("[LectureController] : page : {}", page);
		
		Page<LectureOfficerDTO> lectureDtoList = lectureService.openLectureList(page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(lectureDtoList);
		log.info("[LectureController] : pageInfo : {}", pageInfo);
		
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(lectureDtoList.getContent());

		log.info("[LectureController] : selectEmployeeList end ==================================== ");
		
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	/* 나의 강의 목록 조회 */
	@GetMapping("/myLectureCerti")
	public ResponseEntity<ResponseDTO> getMyLectureCerti(@AuthenticationPrincipal EmployeeDTO employee){		
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", lectureService.getMyLectureCerti(employee.getEmpCode())));
	}
	/* 나의 강의 목록 조회 */
	@GetMapping("/myLecture")
	public ResponseEntity<ResponseDTO> getMyLecture(@RequestParam(name = "page", defaultValue = "1") int page, @AuthenticationPrincipal EmployeeDTO employee){
		
		Page<LectureOfficerDTO> lectureDtoList = lectureService.getMyLecture(page, employee.getEmpCode());
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(lectureDtoList);
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(lectureDtoList.getContent());
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	/* 강의명이 있는 강의 조회 */
	@GetMapping("/lecNameMyLecture")
	public ResponseEntity<ResponseDTO> getLecNameMyLecture(@RequestParam(name = "page", defaultValue = "1") int page, @AuthenticationPrincipal EmployeeDTO employee){
		
		Page<LectureOfficerDTO> lectureDtoList = lectureService.getLecNameMyLecture(page, employee.getEmpCode());
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(lectureDtoList);
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(lectureDtoList.getContent());
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	/* 검색 기능 */
	@GetMapping("/Search")
	public ResponseEntity<ResponseDTO> searchLecName(
			@RequestParam(name="page", defaultValue="1") int page,	
			@RequestParam(name="condition")String condition,
			@RequestParam(name="search") String name,
			@AuthenticationPrincipal EmployeeDTO employee){
		
		Page<LectureOfficerDTO> lectureDtoList= lectureService.searchLecName(page, condition, name, employee.getEmpCode());
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(lectureDtoList);
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(lectureDtoList.getContent());
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	/*수업 회차 조회를 위한 findBylecCode*/
	@GetMapping("/lectureCount/{lecCode}")
	public ResponseEntity<ResponseDTO> getMyLectureCount(@PathVariable Long lecCode ){
		
		
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", lectureService.lectureCount(lecCode)));
		
	}
	/* 교수 수업 강의계획서 작성 */
	@PatchMapping("/lecturePlan/{lecCode}")
	public ResponseEntity<ResponseDTO> updatePlan(@ModelAttribute LectureOfficerDTO lectureDTO, @PathVariable Long lecCode){
		lectureService.updatePlan(lectureDTO,lecCode);
		
		return ResponseEntity.ok()
	            .body(new ResponseDTO(HttpStatus.OK, "계획서가 작성되었습니다."));				
		
	}
	
	/*empName,lecName으로 검색해오기(출석 및 성적 검색 서치바)*/
	@GetMapping("/listSearch")
	public ResponseEntity<ResponseDTO> selectMyLectureSearchName(
			@RequestParam(name="page", defaultValue="1") int page,	
			@RequestParam(name="condition")String condition,
			@RequestParam(name="search") String name)                  {
			
		Page<LectureOfficerDTO> lectureDtoList = lectureService.selectLectureSearchName(page, condition, name);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(lectureDtoList);	
		
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(lectureDtoList.getContent());
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
		
	}
	
	/*empName,sbjName으로 검색해오기(강의 개설)*/
	@GetMapping("/OpenListSearch")
	public ResponseEntity<ResponseDTO> selectMyOpenLectureSearchName(
			@RequestParam(name="page", defaultValue="1") int page,	
			@RequestParam(name="condition")String condition,
			@RequestParam(name="search") String name)                  {
			

		Page<LectureOfficerDTO> lectureDtoList = lectureService.selectOpenLectureSearchName(page, condition, name);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(lectureDtoList);	
		
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(lectureDtoList.getContent());
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
		

}
}
