package kr.co.basedevice.corebase.domain.td;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.QuizTypCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TD_QUIZ")
@SequenceGenerator(name = "SEQGEN_TD_QUIZ", sequenceName = "SEQ_TD_QUIZ", initialValue = 1000, allocationSize = 1)
public class TdQuiz extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 5523368845285769691L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_TD_QUIZ")
	@Column(name = "ROLE_SEQ", nullable = false)
	private Long quizSeq;

	@Column(name = "QUIZ_TYP_CD", nullable = false, length = 35)
	@Enumerated(EnumType.STRING)
	private QuizTypCd quizTypCd;
	
	@Column(name = "QUIZ_TITL", length = 200, nullable = false)
	private String quizTitl;
	
	@Column(name = "QUIZ_CONT", length = 2000, nullable = false)
	private String quizCont;
	
	@Column(name = "QUIZ_ANSWER", length = 128, nullable = false)
	private String quizAnswer;
	
	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@OneToMany(mappedBy = "tdQuiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TdQuizUserMap> tdQuizUserMapList = new ArrayList<>(1);
	
	@OneToMany(mappedBy = "tdQuiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TdActorQuizUse> tdActorQuizUseList = new ArrayList<>(1);
	
}
