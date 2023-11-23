package kr.co.basedevice.corebase.domain.td;

import java.io.Serializable;
import java.time.LocalDateTime;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.WorkStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TD_WORK")
@SequenceGenerator(name = "SEQGEN_TD_WORK", sequenceName = "SEQ_TD_WORK", initialValue = 1000, allocationSize = 1)
public class TdWork extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -5123848958747519005L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_TD_WORK")
	@Column(name = "WORK_SEQ", nullable = false)
	private Long workSeq;
	
	@Column(name = "TODO_SEQ", nullable = false)
	private Long todoSeq;
	
	@Column(name = "WORKER_SEQ", nullable = false)
	private Long workerSeq;
	
	@Column(name = "WORK_TITL", length = 200)
	private String workTitl;
	
	@Column(name = "WORK_CONT", length = 4000)
	private String workCont;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul") //날짜 포멧 바꾸기
	@Column(name = "WORK_DT")
	private LocalDateTime workDt;
	
	@Column(name = "WORK_STAT_CD", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private WorkStatCd workStatCd;
	
	/** 
	 * 작업 가능 시작 시간
	 */
	@Column(name = "WORK_POSS_BEGIN_DT", nullable = false)
	private LocalDateTime workPossBeginDt;
	
	/** 
	 * 작업 가능 종료 시간
	 * 
	 */
	@Column(name = "WORK_POSS_END_DT", nullable = false)
	private LocalDateTime workPossEndDt;
	
	/** 
	 * 확인 내용
	 */
	@Column(name = "CONFM_CONT", length = 2000)
	private String confmCont;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul") //날짜 포멧 바꾸기
	@Column(name = "CONFM_DT")
	private LocalDateTime confmDt;
	
	@Column(name = "GAIN_POINT")
	private Integer gainPoint;
	
	@Column(name = "CHECKER_SEQ")
	private Long checkerSeq;
	
	@Column(name = "SETLE_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn setleYn;
	
	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@OneToMany(mappedBy = "tdWork", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TdWorkSetleMap> tdTodoSetleMapList = new ArrayList<>(1);
	
	@OneToMany(mappedBy = "tdWork", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TdQuizWorkUse> tdWorkerQuizUseList = new ArrayList<>(1);
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "TODO_SEQ", updatable = false, insertable = false)
	private TdTodo tdTodo;	
	
}
