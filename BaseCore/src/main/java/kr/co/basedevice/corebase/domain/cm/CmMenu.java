package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_MENU")
@SequenceGenerator(name = "SEQGEN_CM_MENU", sequenceName = "SEQ_CM_MENU", initialValue = 1000, allocationSize = 1)
public class CmMenu implements Serializable{

	private static final long serialVersionUID = -4796441346567889052L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_MENU")
	@Column(name = "MENU_SEQ", nullable = false)
	private Long menuSeq;
	
	@Column(name = "UP_MENU_SEQ")
	private Long upMenuSeq;
	
	@Column(name = "MENU_PATH", length = 255)
	private String menuPath;
	
	@Column(name = "MENU_NM", length = 30, nullable = false)
	private String menuNm;
	
	@Column(name = "MENU_DESC", length = 2000)
	private String menuDesc;
	
	@Column(name = "ICON_URL", length = 255)
	private String iConUrl;
	
	@Column(name = "CM_SCREN_YN", length = 1, nullable = false)
	@Enumerated(EnumType.STRING)
	private Yn cmScrenYn;
		
	@Column(name = "PRNT_YN", length = 1, nullable = false)
	@Enumerated(EnumType.STRING)
	private Yn prntYn;
	
	@Column(name = "PRNT_ORD", nullable = false)
	private Integer prntOrd;

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
	
	@OneToMany(mappedBy = "cmMenu", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmRoleMenuMap> cmRoleMenuMapList = new ArrayList<>(1);
	
	@OneToMany(mappedBy = "cmMenu", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmMenuDtl> cmMenuDtlList = new ArrayList<>(1);
		
	@OneToMany(mappedBy = "cmMenu", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmUserBookmark> cmUserBookmarkList = new ArrayList<>(1);
	
}
