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
@Table(name = "CM_USER_ROLE_MAP")
@IdClass(CmUserRoleMapId.class)
public class CmUserRoleMap extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 3510177415748359434L;

	@Id
	@Column(name = "USER_SEQ", nullable = false)
	private Long userSeq;

	@Id
	@Column(name = "ROLE_SEQ", nullable = false)
	private Long roleSeq;
	
	@Column(name = "PRNT_ORD", nullable = false)
	private Integer prntOrd;

	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "USER_SEQ", updatable = false, insertable = false)
	private CmUser cmUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "ROLE_SEQ", updatable = false, insertable = false)
	private CmRole cmRole;
	
}
