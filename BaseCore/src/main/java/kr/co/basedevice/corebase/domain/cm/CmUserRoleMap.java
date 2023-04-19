package kr.co.basedevice.corebase.domain.cm;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_USER_ROLE_MAP")
@IdClass(CmUserRoleMapId.class)
public class CmUserRoleMap {
	
	@Id
	@Column(name = "USER_SEQ", nullable = false)
	private Long userSeq;

	@Id
	@Column(name = "ROLE_SEQ", nullable = false)
	private Long roleSeq;


	@Column(name = "DEL_YN", length = 1)
	private String delYn;

	@Column(name = "CREATOR_SEQ", updatable = false)
	private Long creatorSeq;

	@Column(name = "CRE_DT", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime creDt;

	@Column(name = "UPDATOR_SEQ", insertable = false)
	private Long updatorSeq;

	@Column(name = "UPD_DT", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime updDt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "USER_SEQ", updatable = false, insertable = false)
	private CmUser cmUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "ROLE_SEQ", updatable = false, insertable = false)
	private CmRole cmRole;
	
}
