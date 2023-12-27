package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_ROLE")
public class CmRole extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1414919147407264427L;

	@Id
	@Tsid
	@Column(name = "ROLE_SEQ", nullable = false)
	private Long roleSeq;

	@Column(name = "ROLE_NM", length = 30, nullable = false)
	private String roleNm;

	@Column(name = "DEF_PAGE", length = 255)
	private String defPage;
	
	@Column(name = "ROLE_DESC", length = 2000)
	private String roleDesc;

	@Column(name = "DEL_YN", nullable = false, columnDefinition = "VARCHAR(1)")
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@OneToMany(mappedBy = "cmRole", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CmUserRoleMap> cmUserRoleMapList = new ArrayList<>(1);
	
	@OneToMany(mappedBy = "cmRole", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CmRoleMenuMap> cmRoleMenuMapList = new ArrayList<>(1);
		
}
