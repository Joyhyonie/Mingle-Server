package com.greedy.mingle.certi.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.mingle.attendance.dto.LeaveDocDTO;
import com.greedy.mingle.attendance.entity.LeaveDoc;
import com.greedy.mingle.certi.dto.CertiDocDTO;
import com.greedy.mingle.certi.entity.CertiDoc;
import com.greedy.mingle.certi.repository.CertiDocRepository;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.subject.dto.SubjectDTO;
import com.greedy.mingle.subject.entity.Subject;

@Service
public class CertiDocService {
	
	private final CertiDocRepository certiDocRepository;
	private final ModelMapper modelMapper;
	
	public CertiDocService(ModelMapper modelMapper, CertiDocRepository certiDocRepository) {
		this.modelMapper = modelMapper;
		this.certiDocRepository = certiDocRepository;
	}

	public Page<CertiDocDTO> getCertiList(int page) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("certiDocCode").descending());
		Page<CertiDoc> certiList = certiDocRepository.findAll(pageable);
		Page<CertiDocDTO> certiDtoList = certiList.map(certi -> modelMapper.map(certi, CertiDocDTO.class));
		
		return certiDtoList;
	}

	public Page<CertiDocDTO> selectEmployeeCerti(int page, long empCode) {

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("certiDocCode").descending());
		Page<CertiDoc> certiList = certiDocRepository.findByApplyerEmpCode(pageable,empCode);
		Page<CertiDocDTO> certiDtoList = certiList.map(certi -> modelMapper.map(certi, CertiDocDTO.class));

		return certiDtoList;
	}

	@Transactional
	public void updateCertiDoc(Long certiDocCOde, CertiDocDTO certiDocDto) {
		
		java.time.LocalDateTime localDateTime = java.time.LocalDateTime.now();
		java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(localDateTime);

		CertiDoc certiDoc = certiDocRepository.findById(certiDocCOde)
				.orElseThrow(() -> new NullPointerException("해당 문서가 존재하지 않습니다."));
		
		certiDoc.setAccepter(modelMapper.map(certiDocDto.getAccepter(),Employee.class));
		certiDoc.setSignDate(timestamp.toString());
		certiDoc.setDocStatus("승인");
		
		certiDocRepository.save(certiDoc);
	}

	public CertiDocDTO selectCertiDoc(Long certiDocCode) {

		CertiDoc certiDoc = certiDocRepository.findById(certiDocCode)
				.orElseThrow(() -> new NullPointerException("해당 증명서가 존재하지 않습니다."));
		
		CertiDocDTO certiDocDTO = modelMapper.map(certiDoc, CertiDocDTO.class);
		
		return certiDocDTO;
	}

	@Transactional
	public void registCertiDoc(CertiDocDTO certiDocDTO) {
		
		certiDocRepository.save(modelMapper.map(certiDocDTO, CertiDoc.class));
	}

	public Page<CertiDocDTO> selectCertiDocSearchName(int page, String condition, String name) {
		
		if(condition.equals("empName")) {
			Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("certiDocCode").descending());
			Page<CertiDoc> certiDocList = certiDocRepository.findByApplyerEmpName(pageable, name);		
			Page<CertiDocDTO> certiDocDtoDeptList = certiDocList.map(certiDoc -> modelMapper.map(certiDoc, CertiDocDTO.class));
			return certiDocDtoDeptList;
			} else {
				Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("certiDocCode").descending());
				Page<CertiDoc> certiDocList = certiDocRepository.findByCertiFormCertiFormNameContaining(pageable, name);
				Page<CertiDocDTO> certiDocDTOList = certiDocList.map(certiDoc -> modelMapper.map(certiDoc, CertiDocDTO.class));
				return certiDocDTOList;
			}
	}

	public Page<CertiDocDTO> selectMyCertiDocSearchName(int page, String condition, String name, Long empCode) {
				
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("certiDocCode").descending());
		Page<CertiDoc> certiDocList = certiDocRepository.findByApplyerEmpCodeAndCertiFormCertiFormNameContaining(pageable, empCode, name);
		Page<CertiDocDTO> certiDocDTOList = certiDocList.map(certiDoc -> modelMapper.map(certiDoc, CertiDocDTO.class));
		
		return certiDocDTOList;
	}

	

}
