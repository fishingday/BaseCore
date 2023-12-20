package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.basedevice.corebase.domain.BaseEntity;
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
@Table(name = "CM_CD_GRP")
public class CmCdGrp extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -3619896827890455517L;

	@Id
	@Column(name = "GRP_CD", nullable = false, length = 35)
	private String grpCd;

	@Column(name = "GRP_CD_NM", length = 30, nullable = false)
	private String grpCdNm;

	@Column(name = "GRP_CD_DESC", length = 2000)
	private String grpCdDesc;

	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;
		
	@OneToMany(mappedBy = "cmCdGrp", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmCdDtl> cmCdDtlList = new ArrayList<>(1);
}
