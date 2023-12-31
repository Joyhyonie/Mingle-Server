package com.greedy.mingle.student.controller;

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
import com.greedy.mingle.student.dto.StudentDTO;
import com.greedy.mingle.student.service.StudentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	/* 1. 학번으로 학생 목록 조회 - 페이징 */
	@GetMapping("/students")
	public ResponseEntity<ResponseDTO> selectStudentList(@RequestParam(name = "page", defaultValue = "1") int page) {

		log.info("[StudentController] : selectStudentList start ==================================== ");
		log.info("[StudentController] : page : {}", page);

		Page<StudentDTO> studentDtoList = studentService.selectStudentList(page);

		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(studentDtoList);
		
		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(studentDtoList.getContent());

		log.info("[StudentController] : selectStudentList end ============================== ");

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));

	}

	/* 2. 학생 목록 조회 - 학과 기준, 페이징 */
	@GetMapping("/students/department/{deptCode}")
	public ResponseEntity<ResponseDTO> selectStudentListByDepartment(
			@RequestParam(name = "page", defaultValue = "1") int page, @PathVariable Long deptCode) {
		
		log.info("[StudentController] : selectStudentListByDepartment start ==================================== ");
		log.info("[StudentController] : page : {}", page);

		Page<StudentDTO> studentDtoList = studentService.selectStudentListByDepartment(page, deptCode);

		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(studentDtoList);
		
		log.info("[StudentController] : pageInfo : {}", pageInfo);

		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(studentDtoList.getContent());
		
		log.info("[StudentController] : selectStudentListByDepartment end ==================================== ");

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}

	/* 3. 학생 목록 조회 - 학생명 검색 기준, 페이징 */
	@GetMapping("/search")
	public ResponseEntity<ResponseDTO> selectStudentListBySearchName(
			@RequestParam(name="page", defaultValue="1") int page,	
			@RequestParam(name="condition")String condition,
			@RequestParam(name="search") String name){
		
		Page<StudentDTO> studentDtoList = studentService.selectStudentListByDeptName(page, condition, name);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(studentDtoList);

		ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(studentDtoList.getContent());
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}

	/* 4. 학생 상세 조회 - stdCode로 학생 1명 조회 */
	@GetMapping("/students/{stdCode}")
	public ResponseEntity<ResponseDTO> selectStudent(@PathVariable Long stdCode) {

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", studentService.selectStudent(stdCode)));
	}

	/* 5. 학생 신규 등록 */
	@PostMapping("/insert")
	public ResponseEntity<ResponseDTO> insertStudent(@ModelAttribute StudentDTO studentDto) {

		studentService.insertStudent(studentDto);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "등록 성공"));
	}

	/* 6. 학생 정보 수정 */
	@PutMapping("/modify")
	public ResponseEntity<ResponseDTO> updateStudent(@ModelAttribute StudentDTO studentDto) {

		studentService.updateStudent(studentDto);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "등록 성공"));
	}

	/* 7. 학생 정보 삭제 */
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDTO> deleteStudents(@RequestBody List<Long> stdCodes) {
	    try {
	        for (Long stdCode : stdCodes) {
	            studentService.deleteStudent(stdCode);
	        }
	        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "삭제 성공"));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "삭제 실패"));
	    }
	}

}
