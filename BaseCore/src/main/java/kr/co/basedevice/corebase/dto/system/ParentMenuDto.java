package kr.co.basedevice.corebase.dto.system;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "upMenu")
@NoArgsConstructor
public class ParentMenuDto {
	private Long menuSeq;

	private Long upMenuSeq;
		
	private String menuNm;
	
	private Integer prntOrd;
	
	private ParentMenuDto upMenu;
	
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
}
