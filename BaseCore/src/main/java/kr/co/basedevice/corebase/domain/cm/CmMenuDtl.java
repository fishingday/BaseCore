package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.proxy.HibernateProxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_MENU_DTL")
@SequenceGenerator(name = "SEQGEN_CM_MENU_DTL", sequenceName = "SEQ_CM_MENU_DTL", initialValue = 1000, allocationSize = 1)
public class CmMenuDtl extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -220508149793619902L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_MENU_DTL")
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
	
	@Column(name = "DEL_YN", nullable = false, length = 1)
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
