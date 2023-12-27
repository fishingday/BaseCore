package kr.co.basedevice.corebase.domain.td;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.WorkStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TD_WORK")
public class TdWork extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -5123848958747519005L;

	@Id
	@Tsid
	@Column(name = "WORK_SEQ", nullable = false)
	private Long workSeq;
	
	@Column(name = "TODO_SEQ", nullable = false)
	private Long todoSeq;
	
	@Column(name = "WORKER_SEQ", nullable = false)
	private Long workerSeq;
			
	@Column(name = "UP_WORK_SEQ")
	private Long upWorkSeq;
	
	@Column(name = "WORK_TITL", length = 200)
	private String workTitl;
	
	@Column(name = "WORK_CONT", length = 4000)
	private String workCont;
	
	@Column(name = "WORK_RSLT_VAL", length = 4000)
	private String workRsltVal;
		
	@Column(name = "FILE_GRP_SEQ")
	private Long fileGrpSeq;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul") //날짜 포멧 바꾸기
	@Column(name = "WORK_BEGIN_DT")
	private LocalDateTime workBeginDt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul") //날짜 포멧 바꾸기
	@Column(name = "WORK_END_DT")
	private LocalDateTime workEndDt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul") //날짜 포멧 바꾸기
	@Column(name = "WORK_DT")
	private LocalDateTime workDt;
	
	@Column(name = "WORK_STAT_CD", nullable = false, length = 35)
	@Enumerated(EnumType.STRING)
	private WorkStatCd workStatCd;
	
		
	/** 작업 가능 시작 시간 */
	@Column(name = "WORK_POSS_BEGIN_DT", nullable = false)
	private LocalDateTime workPossBeginDt;
	
	/** 작업 가능 종료 시간 */
	@Column(name = "WORK_POSS_END_DT", nullable = false)
	private LocalDateTime workPossEndDt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul") //날짜 포멧 바꾸기
	@Column(name = "CONFM_DT")
	private LocalDateTime confmDt;

	@Column(name = "CONFM_CONT", length = 2000)
	private String confmCont;
	
	@Column(name = "GAIN_POINT")
	private Integer gainPoint;
	
	@Column(name = "CHECKER_SEQ")
	private Long checkerSeq;
	
	@Column(name = "SETLE_YN", nullable = false, columnDefinition = "VARCHAR(1)")
	@Enumerated(EnumType.STRING)
	private Yn setleYn;
	
	@Column(name = "SETLE_SEQ")
	private Long setleSeq;
	
	@Column(name = "DEL_YN", nullable = false, columnDefinition = "VARCHAR(1)")
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@OneToMany(mappedBy = "tdWork", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TdQuizWorkUse> tdWorkerQuizUseList = new ArrayList<>(1);
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "TODO_SEQ", updatable = false, insertable = false)
	private TdTodo tdTodo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "SETLE_SEQ", updatable = false, insertable = false)
	private TdSetle tdSetle;
	
}
