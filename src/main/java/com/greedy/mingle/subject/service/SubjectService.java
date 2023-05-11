package com.greedy.mingle.subject.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.mingle.subject.dto.SubjectDTO;
import com.greedy.mingle.subject.entity.Department;
import com.greedy.mingle.subject.entity.Subject;
import com.greedy.mingle.subject.repository.DepartmentRepository;
import com.greedy.mingle.subject.repository.SubjectRepository;

@Service
public class SubjectService {
	
	private final SubjectRepository subjectRepository;
	private final DepartmentRepository departmentRepository;
	private final ModelMapper modelMapper;
	
	public SubjectService(SubjectRepository subjectRepository,ModelMapper modelMapper,DepartmentRepository departmentRepository) {
		this.subjectRepository = subjectRepository;
		this.departmentRepository = departmentRepository;
		this.modelMapper = modelMapper;
	}

	public Page<SubjectDTO> selectSubjectList(int page) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("sbjCode").descending());
		Page<Subject> subjectList = subjectRepository.findAll(pageable);
		Page<SubjectDTO> subjectDtoList = subjectList.map(subject -> modelMapper.map(subject, SubjectDTO.class));
		
		return subjectDtoList;
	}

	public Page<SubjectDTO> selectSubjectListByDepartment(int page, Long deptCode) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("sbjCode").descending());
		
		Department findDepartment = departmentRepository.findById(deptCode)
									.orElseThrow(()-> new IllegalArgumentException("해당 학과 없습니다. deptCode = "+ deptCode));
		
		Page<Subject> subjectList = subjectRepository.findByDepartment(pageable,findDepartment);
		Page<SubjectDTO> subjectDtoList = subjectList.map(subject -> modelMapper.map(subject, SubjectDTO.class));
		
		return subjectDtoList;
	}

	public Page<SubjectDTO> selectSubjectListBySubjectName(int page, String sbjName) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("sbjCode").descending());
		
		Page<Subject> subjectList = subjectRepository.findBySbjName(pageable,sbjName);
		Page<SubjectDTO> subjectDtoList = subjectList.map(subject -> modelMapper.map(subject, SubjectDTO.class));
		return subjectDtoList;
	}

	@Transactional
	public void updateSubject(SubjectDTO subjectDto) {
		
		Subject findSubject = subjectRepository.findById(subjectDto.getSbjCode())
					.orElseThrow(()-> new IllegalArgumentException("해당 학과 없습니다. SubjectCode = "+ subjectDto.getSbjCode()));
		
		findSubject.update(
				subjectDto.getSbjName(),
				subjectDto.getClassType(),
				subjectDto.getScore(),
				modelMapper.map(subjectDto.getDepartment(), Department.class)
				);		
	}

	@Transactional
	public void insertSubject(SubjectDTO subjectDto) {
		
		subjectRepository.save(modelMapper.map(subjectDto, Subject.class));
		
	}

	@Transactional
	public void deleteSubject(Long sbjCode) {
		
		subjectRepository.deleteById(sbjCode);
	}

}
