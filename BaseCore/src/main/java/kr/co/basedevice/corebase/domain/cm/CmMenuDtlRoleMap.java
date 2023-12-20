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
@Table(name = "CM_MENU_DTL_ROLE_MAP")
@IdClass(CmMenuDtlRoleMapId.class)
public class CmMenuDtlRoleMap extends BaseEntity implements Serializable{
	
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "MENU_DTL_SEQ", updatable = false, insertable = false)
	private CmMenuDtl cmMenuDtl;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "ROLE_SEQ", updatable = false, insertable = false)
	private CmRole cmRole;
	
}
