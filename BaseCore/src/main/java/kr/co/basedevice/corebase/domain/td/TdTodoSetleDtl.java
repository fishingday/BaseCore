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
@Table(name = "TD_TODO_PROC_SETLE")
@IdClass(TdTodoSetleDtlId.class)
public class TdTodoSetleDtl extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -7049311875233855750L;

	@Id
	@Column(name = "TODO_SETLE_SEQ", nullable = false)
	private Long todoSetleSeq;
	
	@Id
	@Column(name = "WORK_SEQ", nullable = false)
	private Long workSeq;
	
	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "TODO_SETLE_SEQ", updatable = false, insertable = false)
	private TdTodoSetle tdTodoSetle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "WORK_SEQ", updatable = false, insertable = false)
	private TdWork tdWork;
}
