package kr.co.basedevice.corebase.service.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.dto.MenuDto;
import kr.co.basedevice.corebase.dto.MyMenuDto;
import kr.co.basedevice.corebase.exception.MenuSettingException;
import kr.co.basedevice.corebase.repository.cm.CmMenuRepository;
import kr.co.basedevice.corebase.repository.cm.CmRoleRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

	final private CmUserRepository cmUserRepository;
	final private CmRoleRepository cmRoleRepository;
	final private CmMenuRepository cmMenuRepository;

	
	public void saveCmUser(CmUser cmUser) {
		cmUserRepository.save(cmUser);
	}

	public List<CmRole> findByUserSeq4CmRole(Long userSeq) {
		return cmRoleRepository.findByUserSeq(userSeq);
	}
	
	/**
	 * 사용자 권한 별 메뉴 목록
	 * - DBMS에 종속적인 뤄리 사용을 배제하고, 단순 쿼리 위주로...
	 * 
	 * @param userSeq
	 * @param roleSeq
	 * @return
	 */	
	public MyMenuDto findRoleMenuWithSetting(Long userSeq, Long roleSeq ) {
		// 일단... 사용자 역할에 할당된 말단 메뉴 목록을 조회하고..
		List<CmMenu> userRoleMenuList = cmMenuRepository.findUserRoleMenu(userSeq, roleSeq);
		
		Map<Long, MenuDto> upMenuMap = new HashMap<>(1);
		
		Set<MenuDto> topMenuSet = new HashSet<>(1); 
		
		for(CmMenu leafMenu : userRoleMenuList) {
			Long upMenuSeq = leafMenu.getUpMenuSeq();
			if(upMenuSeq == null) {
				topMenuSet.add(new MenuDto(leafMenu));
			}else {
				topMenuSet.add(getTopMenu(new MenuDto(leafMenu), upMenuMap));
			}
		}
		
		// 사용자가 가진 메뉴 목록을 포함하는 최상위 메뉴 목록을....
		MyMenuDto myMenuDto = new MyMenuDto(topMenuSet);
				
				
		return myMenuDto;
	}

	/**
	 * 최상위 메뉴를 찾는다.
	 * 
	 * @param leafMenu
	 * @param upMenuMap
	 * @return
	 */
	private MenuDto getTopMenu(MenuDto leafMenu, Map<Long, MenuDto> upMenuMap) {		
		MenuDto upMenu = upMenuMap.get(leafMenu.getUpMenuSeq());
		
		if(upMenu == null) {
			CmMenu cmMenu = cmMenuRepository.findByUpMenuSeqAndDelYn(leafMenu.getUpMenuSeq(), Yn.N);
			if(cmMenu != null) {
				upMenu = new MenuDto(cmMenu);
				upMenuMap.put(cmMenu.getMenuSeq(), upMenu);
			}else {
				throw new MenuSettingException(leafMenu.getMenuNm() + "의 상위 메뉴를 찾을 수 없습니다. : UP_MENU_SEQ - " + leafMenu.getUpMenuSeq() ); // 메뉴 설정 오류 인데...
			}
		}
		
		if(upMenu != null) {
			leafMenu.setUpMenu(upMenu); // 상위 메뉴를 세팅하고..
			upMenu.getSubMenuList().add(leafMenu); // 상위 메뉴 아래 ...
		}
		
		if(upMenu.getUpMenuSeq() != null) { // 조상이 있다.
			return getTopMenu(upMenu, upMenuMap);
		}else{ // 조상이 없다면... top!
			return upMenu;
		}
	}
}
