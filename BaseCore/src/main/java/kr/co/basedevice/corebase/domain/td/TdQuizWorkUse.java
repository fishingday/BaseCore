package kr.co.basedevice.corebase.domain.td;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TD_QUIZ_WORK_USE")
@IdClass(TdQuizWorkUseId.class)
public class TdQuizWorkUse extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5918461434462651471L;

	@Id
	@Column(name = "QUIZ_SEQ", nullable = false)
	private Long quizSeq;
	
	@Id
	@Column(name = "WORK_SEQ", nullable = false)
	private Long workSeq;
	
	@Column(name = "USER_SEQ", nullable = false)
	private Long userSeq;
	
	@Column(name = "DEL_YN", nullable = false, columnDefinition = "VARCHAR(1)")
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "QUIZ_SEQ", updatable = false, insertable = false)
	private TdQuiz tdQuiz;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "WORK_SEQ", updatable = false, insertable = false)
	private TdWork tdWork;
}
