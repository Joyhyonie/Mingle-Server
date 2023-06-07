package com.greedy.mingle.board.entity;

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
@Table(name="TBL_BOARD")
@DynamicInsert
public class Board {
	
	@Id
	@Column(name="BOARD_CODE")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long boardCode;
	
	@Column(name="BOARD_TYPE")
	private String boardType;
	
	@Column(name="BOARD_TITLE")
	private String boardTitle;
	
	@Column(name="BOARD_CONTENT")
	private String boardContent;
	
	@Column(name="BOARD_WRITE_DATE")
	private Date boardWriteDate;
	
	@Column(name="BOARD_COUNT")
	private int boardCount;
	
	@ManyToOne
	@JoinColumn(name="EMP_CODE")
	private Employee writer;
	
	@Column(name="BOARD_STATUS")
	private String boardStatus;
	
	@Column(name="BOARD_MODIFY_DATE")
	private Date boardModifyDate;
	
	
	/* 공지사항 수정용 메소드 */
	public void boardUpdate(String boardType, String boardTitle, String boardContent) {
		
		this.boardType = boardType;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		
	}

}
