package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_ROLE")
@SequenceGenerator(name = "SEQGEN_CM_ROLE", sequenceName = "SEQ_CM_ROLE", initialValue = 1000, allocationSize = 1)
public class CmRole extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1414919147407264427L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_ROLE")
	@Column(name = "ROLE_SEQ", nullable = false)
	private Long roleSeq;

	@Column(name = "ROLE_NM", length = 30, nullable = false)
	private String roleNm;

	@Column(name = "DEF_PAGE", length = 255)
	private String defPage;
	
	@Column(name = "ROLE_DESC", length = 2000)
	private String roleDesc;

	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
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
