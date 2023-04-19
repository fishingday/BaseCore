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
@Table(name = "CM_MENU")
@SequenceGenerator(name = "SEQGEN_CM_MENU_DTL", sequenceName = "SEQ_CM_MENU_DTL", initialValue = 1000, allocationSize = 1)
public class CmMenuDtl {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_MENU_DTL")
	@Column(name = "MENU_DTL_SEQ", nullable = false)
	private Long menuDtlSeq;
	
	@Column(name = "MENU_SEQ", nullable = false)
	private Long menuSeq;
	
	@Column(name = "MENU_DTL_PATH", length = 255, nullable = false)
	private String menuDtlPath;
	

	@Column(name = "MENU_DTL_METH_CD", length = 35, nullable = false)
	private String menuDtlMethCd;

	@Column(name = "MENU_DTL_NM", length = 30, nullable = false)
	private String menuDtlNm;
	
	@Column(name = "MENU_DTL_DESC", length = 2000)
	private String menuDtlDesc;	
	
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
	@JoinColumn(name = "MENU_SEQ", updatable = false, insertable = false)
	private CmMenu cmMenu;
}
