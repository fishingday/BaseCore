package kr.co.basedevice.corebase.dto.system;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "upMenu")
@NoArgsConstructor
public class ParentMenuDto implements Comparable<ParentMenuDto>{
	private Long menuSeq;

	private Long upMenuSeq;
		
	private String menuNm;
	
	private Integer prntOrd;
	
	private ParentMenuDto upMenu;
	
	private int level;
	
	static public ParentMenuDto setCmMenu(CmMenu cmMenu) {
		ParentMenuDto parentMenuDto = new ParentMenuDto();
		
		parentMenuDto.setMenuSeq(cmMenu.getMenuSeq());
		parentMenuDto.setUpMenuSeq(cmMenu.getUpMenuSeq());
		parentMenuDto.setMenuNm(cmMenu.getMenuNm());
		parentMenuDto.setPrntOrd(cmMenu.getPrntOrd());		
		
		return parentMenuDto;
	}
	
	
	public String getFullMenuNm() {
		StringBuilder sb = new StringBuilder();
		
		if(upMenu == null) {
			sb.append(this.menuNm);
		}else {
			if(upMenu.upMenu != null) {
				String upMenuNm = upMenu.getFullMenuNm();
				sb.append(upMenuNm).append(">").append(this.menuNm);
			}else {
				sb.append(upMenu.getMenuNm()).append(">").append(this.menuNm);
			}
		}
		
		return sb.toString();
	}
	
	public String getSiblingOrder() {
		StringBuilder sb = new StringBuilder();
		
		if(upMenu == null) {
			sb.append(this.prntOrd);
		}else {
			if(upMenu.upMenu != null) {
				String siblingOrder = upMenu.getSiblingOrder();
				sb.append(siblingOrder).append(">").append(this.prntOrd);
			}else {
				sb.append(upMenu.getSiblingOrder()).append(">").append(this.prntOrd);
			}
		}
		
		return sb.toString();
	}

	@Override
	public int compareTo(ParentMenuDto target) {
        return this.getSiblingOrder().compareTo(target.getSiblingOrder());
	}
}
