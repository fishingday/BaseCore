package kr.co.basedevice.corebase.domain.cm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

/**
 * 그룹코드는 컬럼명으로 사용 
 * 
 * @author fishingday
 *
 */
@Getter
@Setter
@Entity
@Table(name = "CM_GRP_CD")
public class CmGrpCd {
	
	@Id
	@Column(name = "GRP_CD", nullable = false, length = 35)
	private String grpCd;

	@Column(name = "GRP_CD_NM", length = 30)
	private String grpCdNm;

	@Column(name = "GRP_CD_DESC", length = 2000)
	private String grpCdDesc;

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
	
	@OneToMany(mappedBy = "cmGrpCd", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmCdDtl> cmCdDtlList = new ArrayList<>(1);
}
