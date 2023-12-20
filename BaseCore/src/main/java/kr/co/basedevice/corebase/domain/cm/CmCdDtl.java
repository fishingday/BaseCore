package kr.co.basedevice.corebase.domain.cm;

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
@Table(name = "CM_CD_DTL")
@IdClass(CmCdDtlId.class)
public class CmCdDtl extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 3680235465697884138L;

	@Id
	@Column(name = "GRP_CD", nullable = false, length = 35)
	private String grpCd;
	
	@Id
	@Column(name = "CD", nullable = false, length = 35)
	private String cd;

	@Column(name = "CD_NM", nullable = false, length = 30)
	private String cdNm;

	@Column(name = "CD_DESC", length = 2000)
	private String cdDesc;
	
	@Column(name = "PRNT_ORD", nullable = false)
	private Integer prntOrd;
	
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
		
	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "GRP_CD", updatable = false, insertable = false)
	private CmCdGrp cmCdGrp;
}
