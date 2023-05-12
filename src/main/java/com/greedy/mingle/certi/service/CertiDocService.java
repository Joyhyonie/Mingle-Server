package com.greedy.mingle.certi.service;

import java.time.LocalDate;
import java.util.Date;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.mingle.certi.dto.CertiDocDTO;
import com.greedy.mingle.certi.entity.CertiDoc;
import com.greedy.mingle.certi.entity.CertiForm;
import com.greedy.mingle.certi.repository.CertiDocRepository;
import com.greedy.mingle.employee.entity.Employee;

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

	@Transactional
	public void updateCertiDoc(CertiDocDTO certiDocDto) {

		CertiDoc certiDoc = certiDocRepository.findById(certiDocDto.getCertiDocCode())
				.orElseThrow(()-> new IllegalArgumentException("증명서 코드가 없습니다."));
		LocalDate now = LocalDate.now();
		certiDocDto.setSignDate(now.toString());
		certiDoc.update(
				certiDocDto.getCertiApplyDate(),
				certiDocDto.getSignDate(),
				certiDocDto.getDocStatus(),
				certiDocDto.getReason(),
				modelMapper.map(certiDocDto.getApplyer(), Employee.class),
				modelMapper.map(certiDocDto.getAccepter(), Employee.class),
				modelMapper.map(certiDocDto.getCertiForm(), CertiForm.class),
				certiDocDto.getCertiUse()
		);
	}

}
