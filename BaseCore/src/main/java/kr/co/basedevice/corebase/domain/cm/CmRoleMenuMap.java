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
@Table(name = "CM_ROLE_MENU_MAP")
@IdClass(CmRoleMenuMapId.class)
public class CmRoleMenuMap extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 7355846365503832864L;

	@Id
	@Column(name = "ROLE_SEQ", nullable = false)
	private Long roleSeq;
	
	@Id
	@Column(name = "MENU_SEQ", nullable = false)
	private Long menuSeq;

	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "ROLE_SEQ", updatable = false, insertable = false)
	private CmRole cmRole;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "MENU_SEQ", updatable = false, insertable = false)
	private CmMenu cmMenu;

}
