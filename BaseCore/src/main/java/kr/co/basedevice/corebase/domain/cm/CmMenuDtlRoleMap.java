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
@Table(name = "CM_MENU_DTL_ROLE_MAP")
@IdClass(CmMenuDtlRoleMapId.class)
public class CmMenuDtlRoleMap implements Serializable{
	
	private static final long serialVersionUID = -1643591832531083795L;

	@Id
	@Column(name = "MENU_DTL_SEQ", nullable = false)
	private Long menuDtlSeq;

	@Id
	@Column(name = "ROLE_SEQ", nullable = false)
	private Long roleSeq;
	
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
	@JoinColumn(name = "MENU_DTL_SEQ", updatable = false, insertable = false)
	private CmMenuDtl cmMenuDtl;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "ROLE_SEQ", updatable = false, insertable = false)
	private CmRole cmRole;
	
}
