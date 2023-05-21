package com.greedy.mingle.message.entity;

import java.util.Date;

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
@Table(name="TBL_MESSAGE")
@SequenceGenerator(name="MESSAGE_SEQ_GENERATOR",
				   sequenceName="SEQ_MSG_CODE",
				   initialValue=1, allocationSize=1)
@DynamicInsert
public class Message {
	
	
	@Id
	@Column(name="MSG_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MESSAGE_SEQ_GENERATOR")
	private Long msgCode;
	
	@Column(name="MSG_CONTENT")
	private String msgContent;
	
	@Column(name="MSG_SEND_DATE")
	private Date msgSendDate;
	
	@Column(name="MSG_IMP_SENDER")
	private String msgImpSender;
	
	@Column(name="MSG_IMP_RECEIVER")
	private String msgImpReceiver;
	
	@Column(name="MSG_READ_YN")
	private String msgReadYn;
	
	@ManyToOne
	@JoinColumn(name="MSG_SENDER")
	private Employee sender;
	
	@ManyToOne
	@JoinColumn(name="MSG_RECEIVER")
	private Employee receiver;
	
	@Column(name="MSG_DEL_SENDER")
	private String msgDelSender;
	
	@Column(name="MSG_DEL_RECEIVER")
	private String msgDelReceiver;
	
	
	/* 읽음 표시용 메소드 */
	public void readMessage(String msgReadYn) {
		this.msgReadYn = msgReadYn;
	}

	
}
