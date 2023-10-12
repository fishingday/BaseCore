package kr.co.basedevice.corebase.domain.td;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TD_QUIZ_USER_MAP")
@SequenceGenerator(name = "SEQGEN_TD_QUIZ_USER_MAP", sequenceName = "SEQ_TD_QUIZ_USER_MAP", initialValue = 1000, allocationSize = 1)
public class TdQuizUserMap extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -4797049962761909067L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_ROLE")
	@Column(name = "QUIZ_USER_SEQ", nullable = false)
	private Long quizUserSeq;
	
	@Column(name = "QUIZ_SEQ", nullable = false)
	private Long quizSeq;
	
	@Column(name = "USER_SEQ", nullable = false)
	private Long userSeq;
	
	@Column(name = "USER_ANSWER", length = 128, nullable = false)
	private String userAnswer;
	
	@Column(name = "ANSWER_CNT", nullable = false)
	private Integer answerCnt;
	
	@Column(name = "SUCES_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn sucesYn;
	
	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "QUIZ_SEQ", updatable = false, insertable = false)
	private TdQuiz tdQuiz;

}
