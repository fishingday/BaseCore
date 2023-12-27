package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.proxy.HibernateProxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_MENU_DTL")
public class CmMenuDtl extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -220508149793619902L;

	@Id
	@Tsid
	@Column(name = "MENU_DTL_SEQ", nullable = false)
	private Long menuDtlSeq;
	
	@Column(name = "MENU_SEQ", nullable = false)
	private Long menuSeq;
	
	@Column(name = "MENU_DTL_PATH", length = 255, nullable = false)
	private String menuDtlPath;
	
	@Column(name = "MENU_DTL_NM", length = 30, nullable = false)
	private String menuDtlNm;
	
	@Column(name = "MENU_DTL_DESC", length = 2000)
	private String menuDtlDesc;	
	
	@Column(name = "DEL_YN", nullable = false, columnDefinition = "VARCHAR(1)")
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "MENU_SEQ", updatable = false, insertable = false)
	private CmMenu cmMenu;
	
	@OneToMany(mappedBy = "cmMenuDtl", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmMenuDtlRoleMap> cmMenuDtlRoleMapList = new ArrayList<>(1);
	
	
	//------------
	/**
	 * 깊이를 반환한다.
	 * 
	 * @return
	 */
	public int getDepth() {
		if(cmMenu == null || cmMenu instanceof HibernateProxy  ) {
			return -1;
		}
		
		String menuUrl = this.cmMenu.getMenuPath() + this.menuDtlPath; 
		
		if(this.cmMenu != null) {
			return menuUrl.length() - menuUrl.replace("/", "").length();
		}else {
			return -1;
		}
	} 
}
