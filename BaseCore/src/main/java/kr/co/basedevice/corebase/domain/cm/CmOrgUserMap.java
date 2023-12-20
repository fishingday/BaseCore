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
@Table(name = "CM_ORG_USER_MAP")
@IdClass(CmOrgUserMapId.class)
public class CmOrgUserMap extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5053209812644205924L;

	@Id
	@Column(name = "ORG_SEQ", nullable = false)
	private Long orgSeq;
	
	@Id
	@Column(name = "USER_SEQ", nullable = false)
	private Long userSeq;
	
	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "ORG_SEQ", updatable = false, insertable = false)
	private CmOrg cmOrg;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "USER_SEQ", updatable = false, insertable = false)
	private CmUser cmUser;
}
