package kr.co.basedevice.corebase.dto;

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
@ToString
@NoArgsConstructor
public class MenuDto implements Comparable<MenuDto>{
	
	private Long menuSeq;

	private Long upMenuSeq;
	
	private String menuPath;
	
	private String menuNm;
	
	private String menuDesc;

	private String iConUrl;
	
	private Yn prntYn;
		
	private Yn cmScrenYn;
	
	private Integer prntOrd;
	
	List<MenuDto> subMenuList = new ArrayList<>();

	private MenuDto upMenu;
	
	public MenuDto(CmMenu cmMenu) {
		this.menuSeq = cmMenu.getMenuSeq();
		this.upMenuSeq = cmMenu.getUpMenuSeq();
		this.menuPath = cmMenu.getMenuPath();
		this.menuNm = cmMenu.getMenuNm();
		this.menuDesc = cmMenu.getMenuDesc();
		this.iConUrl = cmMenu.getIConUrl();
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
		Collections.sort(subMenuList);
		return subMenuList;
	}
}
