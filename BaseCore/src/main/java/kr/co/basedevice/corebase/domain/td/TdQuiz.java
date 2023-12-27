package kr.co.basedevice.corebase.domain.td;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.QuizTypCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TD_QUIZ")
@NoArgsConstructor
public class TdQuiz extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 5523368845285769691L;
	
	@Id
	@Tsid
	@Column(name = "QUIZ_SEQ", nullable = false)
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
	
	@Column(name = "DEL_YN", nullable = false, columnDefinition = "VARCHAR(1)")
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@OneToMany(mappedBy = "tdQuiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TdQuizWorkUse> tdWorkerQuizUseList = new ArrayList<>(1);

	public TdQuiz(Long quizSeq, QuizTypCd quizTypCd, String quizTitl, String quizCont, String quizAnswer) {
		this.quizSeq = quizSeq;
		this.quizTypCd = quizTypCd; 
		this.quizTitl = quizTitl;
		this.quizCont = quizCont;
		this.quizAnswer = quizAnswer;
	}
}
