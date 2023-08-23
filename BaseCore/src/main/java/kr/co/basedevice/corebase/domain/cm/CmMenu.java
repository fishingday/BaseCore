package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

/**
 * 메뉴 객체
 * - 기본적인 메뉴는 menuSeq 를 정렬하는 것 만으로 정렬이 될 수 있도록 함.
 * - prntYn은 사용자의 목록에 표시 되어야 하는 메뉴임.
 * - cmScrenYn는 모든 사용자가 사용할 수 있는 화면을 표시하는 것임.
 * 
 * @author fishingday
 *
 */
@Getter
@Setter
@Entity
@Table(name = "CM_MENU")
@SequenceGenerator(name = "SEQGEN_CM_MENU", sequenceName = "SEQ_CM_MENU", initialValue = 10000, allocationSize = 1)
public class CmMenu extends BaseEntity implements Serializable{

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

	@Column(name = "ICON_INFO", length = 255)
	private String iConInfo;
	
	@Column(name = "PRNT_YN", length = 1, nullable = false)
	@Enumerated(EnumType.STRING)
	private Yn prntYn;
		
	@Column(name = "CM_SCREN_YN", length = 1, nullable = false)
	@Enumerated(EnumType.STRING)
	private Yn cmScrenYn;
	
	@Column(name = "PRNT_ORD", nullable = false)
	private Integer prntOrd;

	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@OneToMany(mappedBy = "cmMenu", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmRoleMenuMap> cmRoleMenuMapList = new ArrayList<>(1);
	
	@OneToMany(mappedBy = "cmMenu", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmMenuDtl> cmMenuDtlList = new ArrayList<>(1);
		
	@OneToMany(mappedBy = "cmMenu", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmUserBookmark> cmUserBookmarkList = new ArrayList<>(1);
	
	//------------
	/**
	 * 깊이를 반환한다.
	 * 
	 * @return
	 */
	public int getDepth() {
		if(this.menuPath != null) {
			return this.menuPath.length() - this.menuPath.replace("/", "").length();
		}else {
			return -1;
		}
	}
	
}
