package kr.co.basedevice.corebase.domain.td;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.PointUseCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TD_POINT_USE")
@SequenceGenerator(name = "SEQGEN_TD_POINT_USE", sequenceName = "SEQ_TD_POINT_USE", initialValue = 1000, allocationSize = 1)
public class TdPointUse extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 263907859673969324L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_TD_POINT_USE")
	@Column(name = "POINT_USE_SEQ", nullable = false)
	private Long pointUseSeq;
	
	@Column(name = "USER_SEQ", nullable = false)
	private Long userSeq;
	
	@Column(name = "USE_POINT", nullable = false)
	private Integer usePoint;

	@Column(name = "POINT_USE_CD", nullable = false, length = 35)
	@Enumerated(EnumType.STRING)
	private PointUseCd pointUseCd;
		
	@Column(name = "POINT_USE_CONT", length = 2000)
	private String pointUseCont;
		
	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;
}
