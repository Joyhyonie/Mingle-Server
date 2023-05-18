package com.greedy.mingle.certi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.greedy.mingle.employee.entity.Employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="TBL_CERTI_DOC")
@DynamicInsert
public class CertiDoc {

	@Id
	@Column(name="CERTI_DOC_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CERTI_DOC_CODE")
	@SequenceGenerator(name="SEQ_CERTI_DOC_CODE", sequenceName="SEQ_CERTI_DOC_CODE", allocationSize=1)
	private Long certiDocCode;
	
	@Column(name="CERTI_APPLY_DATE")
	private String certiApplyDate;
	
	@Column(name="CERTI_SIGN_DATE")
	private String signDate;
	
	@Column(name="CERTI_DOC_STATUS")
	private String docStatus;
	
	@Column(name="CERTI_REASON")
	private String reason;
	
	@ManyToOne
	@JoinColumn(name="CERTI_APPLYER")
	private Employee applyer;
	
	@ManyToOne
	@JoinColumn(name="CERTI_ACCEPTER")
	private Employee accepter;
	
	@ManyToOne
	@JoinColumn(name="CERTI_FORM_CODE")
	private CertiForm certiForm;
	
	@Column(name="CERTI_USE")
	private String certiUse;

	public void update(String certiApplyDate, String signDate, String docStatus, String reason, Employee applyer,
			Employee accepter, CertiForm certiForm, String certiUse) {
		this.certiApplyDate = certiApplyDate;
		this.signDate = signDate;
		this.docStatus = docStatus;
		this.reason = reason;
		this.applyer = applyer;
		this.accepter = accepter;
		this.certiForm = certiForm;
		this.certiUse = certiUse;				
	}
}
