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
@Table(name = "TD_CHECKER_MAP")
@IdClass(TdCheckerMapId.class)
public class TdCheckerMap extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6688519416637991812L;

	@Id
	@Column(name = "TODO_SEQ", nullable = false)
	private Long todoSeq;
	
	@Id
	@Column(name = "CHECKER_SEQ", nullable = false)
	private Long checkerSeq;
	
	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "TODO_SEQ", updatable = false, insertable = false)
	private TdTodo tdTodo;
}
