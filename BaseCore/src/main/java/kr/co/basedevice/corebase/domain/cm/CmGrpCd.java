package kr.co.basedevice.corebase.domain.cm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_GRP_CD", uniqueConstraints = {@UniqueConstraint(name = "UK_CM_GRP_CD_GRP_CD", columnNames = { "GRP_CD" }) })
@SequenceGenerator(name = "SEQGEN_CM_GRP_CD", sequenceName = "SEQ_CM_GRP_CD", initialValue = 1000, allocationSize = 1)
public class CmGrpCd {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_GRP_CD")
	@Column(name = "GRP_CD_SEQ", nullable = false)
	private Long grpCdSeq;

	@Column(name = "GRP_CD", nullable = false, length = 35, unique = true)
	private String grpCd;

	@Column(name = "GRP_CD_NM", length = 30)
	private String grpCdNm;

	@Column(name = "GRP_CD_DESC", length = 2000)
	private String grpCdDesc;

	@Column(name = "DEL_YN", length = 1)
	private String delYn;

	@Column(name = "CREATOR_SEQ", updatable = false)
	private Long creatorSeq;

	@Column(name = "CRE_DT", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creDt;

	@Column(name = "UPDATOR_SEQ")
	private Long updatorSeq;

	@Column(name = "UPD_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updDt;
	
	@OneToMany(mappedBy = "cmGrpCd", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmCdDtl> cmCdDtlList = new ArrayList<>(1);
}
