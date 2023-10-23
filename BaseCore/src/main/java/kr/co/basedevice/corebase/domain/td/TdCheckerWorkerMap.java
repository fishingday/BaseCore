package kr.co.basedevice.corebase.domain.td;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TD_CHECKER_WORKER_MAP")
@IdClass(TdCheckerWorkerMapId.class)
public class TdCheckerWorkerMap extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 759263421871638988L;

	@Id
	@Column(name = "CHECKER_SEQ", nullable = false)
	private Long checkerSeq;
	
	@Id
	@Column(name = "WORKER_SEQ", nullable = false)
	private Long workerSeq;
		
	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;
}
