package kr.co.basedevice.corebase.domain.td;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.PointAplytoCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TD_POINT")
@SequenceGenerator(name = "SEQGEN_TD_POINT", sequenceName = "SEQ_TD_POINT", initialValue = 1000, allocationSize = 1)
public class TdPoint extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 263907859673969324L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_TD_POINT")
	@Column(name = "POINT_SEQ", nullable = false)
	private Long pointSeq;
	
	@Column(name = "USER_SEQ", nullable = false)
	private Long userSeq;
	
	@Column(name = "SETLE_SEQ")
	private Long setleSeq;
	
	@Column(name = "POINT_APLYTO_CD", nullable = false, length = 35)
	@Enumerated(EnumType.STRING)
	private PointAplytoCd pointAplytoCd;
	
	@Column(name = "LAST_POINT", nullable = false)
	private Integer lastPoint;
	
	@Column(name = "APLYTO_POINT", nullable = false)
	private Integer aplytoPoint;

	@Column(name = "POINT_CONT", length = 2000)
	private String pointCont;
				
	@Column(name = "DEL_YN", nullable = false, columnDefinition = "VARCHAR(1)")
	@Enumerated(EnumType.STRING)
	private Yn delYn;
}
