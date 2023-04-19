package kr.co.basedevice.corebase.domain.cm;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_CD_DTL")
@SequenceGenerator(name = "SEQGEN_CM_CD_DTL", sequenceName = "SEQ_CM_CD_DTL", initialValue = 1000, allocationSize = 1)
public class CmCdDtl {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_CD_DTL")
	@Column(name = "CD_DTL_SEQ", nullable = false)
	private Long cdDtlSeq;
	
	@Column(name = "GRP_CD_SEQ", nullable = false)
	private Long grpCdSeq;

	@Column(name = "CD", nullable = false, length = 35)
	private String cd;

	@Column(name = "CD_NM", length = 30, nullable = false)
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
	
	@Column(name = "PRNT_ORD")
	private Integer prntOrd;
	
	@Column(name = "DEL_YN", length = 1)
	private String delYn;

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
	@JoinColumn(name = "GRP_CD_SEQ", updatable = false, insertable = false)
	private CmGrpCd cmGrpCd;
}
