package kr.co.basedevice.corebase.dto;

import java.util.List;

import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDto {
	
	private Long menuSeq;

	private Long upMenuSeq;
	
	private String menuPath;
	
	private String menuNm;
	
	private String menuDesc;

	private String iConUrl;
	
	private Yn prntYn;
		
	private Yn cmScrenYn;
	
	private Integer prntOrd;
	
	List<MenuDto> subMenuList = null;

	private MenuDto topMenu;
}
