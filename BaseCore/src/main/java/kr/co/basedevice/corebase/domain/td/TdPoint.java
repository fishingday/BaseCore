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
	
	@Column(name = "BANK_NM", length = 30)
	private String bankNm;
	
	@Column(name = "BANK_ACOUNT_NO", length = 20)
	private String bankAcountNo;
	
	@Column(name = "BANK_OWNER_NM", length = 30)
	private String bankOwnerNm;
			
	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;
}
