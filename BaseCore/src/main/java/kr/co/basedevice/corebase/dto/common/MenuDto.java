package kr.co.basedevice.corebase.dto.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "upMenu")
@NoArgsConstructor
public class MenuDto implements Comparable<MenuDto>, Serializable{
	
	private static final long serialVersionUID = -7693749358822913212L;

	private Long menuSeq;

	private Long upMenuSeq;
	
	private String menuPath;
	
	private String menuNm;
	
	private String menuDesc;

	private String iConInfo;
	
	private Yn prntYn;
		
	private Yn cmScrenYn;
	
	private Integer prntOrd;
	
	List<MenuDto> subMenuList = new ArrayList<>(1);

	private MenuDto upMenu;

	public MenuDto(CmMenu cmMenu) {
		this.menuSeq = cmMenu.getMenuSeq();
		this.upMenuSeq = cmMenu.getUpMenuSeq();
		this.menuPath = cmMenu.getMenuPath();
		this.menuNm = cmMenu.getMenuNm();
		this.menuDesc = cmMenu.getMenuDesc();
		this.iConInfo = cmMenu.getIConInfo();
		this.prntYn = cmMenu.getPrntYn();
		this.cmScrenYn = cmMenu.getCmScrenYn();
		this.prntOrd = cmMenu.getPrntOrd();
	}

	@Override
	public int compareTo(MenuDto menuDto) {
        if (menuDto.prntOrd < prntOrd) {
            return 1;
        } else if (menuDto.prntOrd > prntOrd) {
            return -1;
        }
		return 0;
	}
	
	/**
	 * 정렬 된다고 믿으면 될까요?
	 * 
	 * @return
	 */
	public List<MenuDto> getSubMenuList(){
		return subMenuList;
	}
	
	/**
	 * 넣을 때 마다 정렬한다.
	 * 
	 * @param menuDto
	 */
	public void addSubMenu(MenuDto menuDto) {
		this.subMenuList.add(menuDto);
		Collections.sort(subMenuList);
	}
	
	/**
	 * 최초의 메뉴 URI를 반환
	 * 
	 * @return
	 */
	public MenuDto firstMenu() {
		if(!subMenuList.isEmpty()) {
			return subMenuList.get(0).firstMenu();
		}else {
			return this;
		}
	}
	
	/**
	 * 최상위 부모 메뉴를 반환
	 * 
	 * @return
	 */
	public MenuDto topMenu() {
		if(this.upMenu == null) {
			return this;
		}else{
			return upMenu.topMenu();
		}
	}
	
	/**
	 * 내가 최상위 일까?
	 * 
	 * @return
	 */
	public boolean isTop() {
		return this.upMenu == null;
	}
}
