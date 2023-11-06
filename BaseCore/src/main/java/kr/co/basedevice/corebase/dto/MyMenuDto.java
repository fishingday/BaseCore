package kr.co.basedevice.corebase.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kr.co.basedevice.corebase.dto.common.MenuDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MyMenuDto implements Serializable{
	
	private static final long serialVersionUID = -6471522938276314617L;

	private List<MenuDto> topMenuList = new LinkedList<>();
	private Map<Long, MenuDto> myMenuMap = new HashMap<>();
	
	/**
	 * 탑메뉴 셋을 받아 
	 * 
	 * @param topMenuSet
	 */
	public MyMenuDto(Set<MenuDto> topMenuSet) {
		topMenuList.addAll(topMenuSet);
		Collections.sort(topMenuList, new MenuDtoComparator());
		
		Iterator<MenuDto> it = topMenuSet.iterator();
		while(it.hasNext()) {
			MenuDto menuDto = it.next();
			myMenuMap.put(menuDto.getMenuSeq(), menuDto);
			if(menuDto.getSubMenuList() != null && !menuDto.getSubMenuList().isEmpty()) {
				this.takeApart(menuDto.getSubMenuList());
			}
		}
	}

	private void takeApart(List<MenuDto> subMenuList) {
		for(MenuDto menuDto: subMenuList) {
			myMenuMap.put(menuDto.getMenuSeq(), menuDto);
			if(menuDto.getSubMenuList() != null && !menuDto.getSubMenuList().isEmpty()) {
				this.takeApart(menuDto.getSubMenuList());
			}
		}
	}

	public MenuDto getMenuDto(Long menuSeq) {
		return myMenuMap.get(menuSeq);
	}
	
}

class MenuDtoComparator implements Comparator<MenuDto> {
    @Override
    public int compare(MenuDto m1, MenuDto m2) {
        if (m1.getPrntOrd() > m2.getPrntOrd()) {
            return 1;
        } else if (m1.getPrntOrd() < m2.getPrntOrd()) {
            return -1;
        }
        return 0;
    }
}