package kr.co.basedevice.corebase.domain.td;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	
	@Column(name = "DEL_YN", nullable = false, length = 1)
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
