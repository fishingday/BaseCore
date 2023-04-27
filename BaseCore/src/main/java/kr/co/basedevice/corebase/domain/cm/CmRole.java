package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.basedevice.corebase.domain.code.RoleCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_ROLE", uniqueConstraints = {@UniqueConstraint(name = "UK_CM_ROLE_ROLE_CD", columnNames = { "ROLE_CD" }) })
@SequenceGenerator(name = "SEQGEN_CM_USER", sequenceName = "SEQ_CM_ROLE", initialValue = 1000, allocationSize = 1)
public class CmRole implements Serializable{

	private static final long serialVersionUID = 1414919147407264427L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_USER")
	@Column(name = "ROLE_SEQ", nullable = false)
	private Long roleSeq;

	@Column(name = "ROLE_CD", nullable = false, length = 35, unique = true)
	@Enumerated(EnumType.STRING)
	private RoleCd roleCd;

	@Column(name = "ROLE_NM", length = 30)
	private String roleNm;

	@Column(name = "ROLE_DESC", length = 2000)
	private String roleDesc;

	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;

	@Column(name = "CREATOR_SEQ", nullable = false, updatable = false)
	private Long creatorSeq;

	@Column(name = "CRE_DT", nullable = false, updatable = false)
	private LocalDateTime creDt;

	@Column(name = "UPDATOR_SEQ", nullable = false)
	private Long updatorSeq;

	@Column(name = "UPD_DT", nullable = false)
	private LocalDateTime updDt;
	
	@OneToMany(mappedBy = "cmRole", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CmUserRoleMap> cmUserRoleMapList = new ArrayList<>(1);
	
	@OneToMany(mappedBy = "cmRole", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CmRoleMenuMap> cmRoleMenuMapList = new ArrayList<>(1);
	
	@OneToMany(mappedBy = "cmRole", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CmUserBookmark> cmUserBookmarkList = new ArrayList<>(1);
	
}
