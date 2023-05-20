package com.greedy.mingle.attendance.entity;

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

@Entity
@Table(name="TBL_LEAVE_DOC")
@Getter
@Setter
@DynamicInsert
public class LeaveDoc {
	
	@Id
	@Column(name="LEAVE_DOC_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_LEAVE_DOC_CODE")
	@SequenceGenerator(name="SEQ_LEAVE_DOC_CODE", sequenceName="SEQ_LEAVE_DOC_CODE", allocationSize=1)
	private Long leaveDocCode;
	
	@Column(name="LEAVE_APPLY_DATE")
	private String applyDate;
	
	@Column(name="LEAVE_SIGN_DATE")
	private String signDate;
	
	@Column(name="LEAVE_DOC_STATUS")
	private String docStatus;
	
	@Column(name="LEAVE_REASON")
	private String reason;
	
	@ManyToOne
	@JoinColumn(name="APPLY_FORM_CODE")
	private ApplyForm applyForm;
	
	@Column(name="LEAVE_START_DATE")
	private String startDate;
	
	@Column(name="LEAVE_END_DATE")
	private String endDate;
	
	@ManyToOne
	@JoinColumn(name="LEAVE_APPLYER")
	private Employee leaveApplyer;
	
	@ManyToOne
	@JoinColumn(name="LEAVE_ACCEPTER")
	private Employee accepter;

	public void update(String applyDate, String signDate, String docStatus, String reason, ApplyForm applyForm, String startDate, String endDate, Employee leaveApplyer, Employee accepter) {
		this.applyDate = applyDate;
		this.signDate = signDate;
		this.docStatus = docStatus;
		this.reason = reason;
		this.applyForm = applyForm;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leaveApplyer = leaveApplyer;
		this.accepter = accepter;
	}
	
}
