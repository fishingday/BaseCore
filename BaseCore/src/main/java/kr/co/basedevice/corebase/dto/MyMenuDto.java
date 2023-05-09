package kr.co.basedevice.corebase.dto;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MyMenuDto{
	
	/**
	 * 탑메뉴 셋을 받아 
	 * 
	 * @param topMenuSet
	 */
	public MyMenuDto(Set<MenuDto> topMenuSet) {
		topMenuList.addAll(topMenuSet);
		Collections.sort(topMenuList, new MenuDtoComparator());
	}

	private List<MenuDto> topMenuList = new LinkedList<>();
	
	private MenuDto currMenu;

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