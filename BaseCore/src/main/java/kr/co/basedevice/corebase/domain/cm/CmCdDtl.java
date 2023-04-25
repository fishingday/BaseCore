package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
import java.time.LocalDateTime;

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

import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_CD_DTL")
@IdClass(CmCdDtlId.class)
public class CmCdDtl implements Serializable {
	
	private static final long serialVersionUID = 3680235465697884138L;

	@Id
	@Column(name = "GRP_CD", nullable = false, length = 35)
	private String grpCd;
	
	@Id
	@Column(name = "CD", nullable = false, length = 35)
	private String cd;

	@Column(name = "CD_NM", nullable = false, length = 30)
	private String CdNm;

	@Column(name = "CD_DESC", length = 2000)
	private String cdDesc;
	
	@Column(name = "OPT_1", length = 256)
	private String opt1;

	@Column(name = "OPT_2", length = 256)
	private String opt2;
	
	@Column(name = "OPT_3", length = 256)
	private String opt3;
	
	@Column(name = "OPT_4", length = 256)
	private String opt4;

	@Column(name = "OPT_5", length = 256)
	private String opt5;
	
	@Column(name = "PRNT_ORD", nullable = false)
	private Integer prntOrd;
	
	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;

	@Column(name = "CREATOR_SEQ", updatable = false)
	private Long creatorSeq;

	@Column(name = "CRE_DT", updatable = false)
	private LocalDateTime creDt;

	@Column(name = "UPDATOR_SEQ")
	private Long updatorSeq;

	@Column(name = "UPD_DT")
	private LocalDateTime updDt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "GRP_CD", updatable = false, insertable = false)
	private CmGrpCd cmGrpCd;
}
