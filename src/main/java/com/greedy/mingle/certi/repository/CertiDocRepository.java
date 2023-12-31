package com.greedy.mingle.certi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.mingle.certi.entity.CertiDoc;

public interface CertiDocRepository extends JpaRepository <CertiDoc,Long>{
	
	Page<CertiDoc> findByApplyerEmpCode(Pageable pageable, Long empCode);

	Page<CertiDoc> findByApplyerEmpName(Pageable pageable, String name);

	Page<CertiDoc> findByCertiFormCertiFormNameContaining(Pageable pageable, String name);

	Page<CertiDoc> findByApplyerEmpCodeAndCertiFormCertiFormNameContaining(Pageable pageable, Long empCode, String name);

}
