package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.UserRelatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_USER_RELAT")
@IdClass(CmUserRelatId.class)
public class CmUserRelat extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1998122612243470139L;

	@Id
	@Column(name = "RELATOR_SEQ", nullable = false)
	private Long relatorSeq;

	@Id
	@Column(name = "TARGETER_SEQ", nullable = false)
	private Long targeterSeq;
	
	@Column(name = "USER_RELAT_CD", length = 35, nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRelatCd userRelatCd;

	@Column(name = "USER_RELAT_DTL_VAL", length = 20, nullable = false)
	private String userRelatDtlVal;
	
	@Column(name = "RELAT_APLY_DT")
	private LocalDateTime relatAplyDt;
	
	@Column(name = "TARGETER_AGRE_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn targeterAgreYn;
	
	@Column(name = "TARGETER_AGRE_DT")
	private LocalDateTime targeterAgreDt;
	
	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;
}
