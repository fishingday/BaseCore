package kr.co.basedevice.corebase.service.common;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.cm.CmUserRoleMap;
import kr.co.basedevice.corebase.domain.code.RoleCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.dto.MyMenuDto;
import kr.co.basedevice.corebase.dto.common.MenuDto;
import kr.co.basedevice.corebase.dto.common.UserInfoDto;
import kr.co.basedevice.corebase.dto.system.SaveUserInfo;
import kr.co.basedevice.corebase.exception.MenuSettingException;
import kr.co.basedevice.corebase.repository.cm.CmMenuRepository;
import kr.co.basedevice.corebase.repository.cm.CmRoleRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserRoleMapRepository;
import kr.co.basedevice.corebase.search.common.SearchUserInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

	final private CmUserRepository cmUserRepository;
	final private CmRoleRepository cmRoleRepository;
	final private CmUserRoleMapRepository cmUserRoleMapRepository;  
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
	public MyMenuDto findRolesMenuWithSetting(Long userSeq, Long roleSeq ) {
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
			CmMenu cmMenu = cmMenuRepository.findByMenuSeqAndDelYn(leafMenu.getUpMenuSeq(), Yn.N);
			if(cmMenu != null) {
				upMenu = new MenuDto(cmMenu);
				upMenuMap.put(cmMenu.getMenuSeq(), upMenu);
			}else {
				throw new MenuSettingException(leafMenu.getMenuNm() + "의 상위 메뉴를 찾을 수 없습니다. : UP_MENU_SEQ - " + leafMenu.getUpMenuSeq() ); // 메뉴 설정 오류 인데...
			}
		}
		
		if(upMenu != null) {
			leafMenu.setUpMenu(upMenu); // 상위 메뉴를 세팅하고..
			upMenu.addSubMenu(leafMenu); // 상위 메뉴 아래 ...
		}
		
		if(upMenu.getUpMenuSeq() != null) { // 조상이 있다.
			return getTopMenu(upMenu, upMenuMap);
		}else{ // 조상이 없다면... top!
			return upMenu;
		}
	}
	
	/**
	 * 사용자 목록 조회
	 * 
	 * @param searchUserInfo
	 * @param pageable
	 * @return
	 */
	public Page<UserInfoDto> pageUserInfo(SearchUserInfo searchUserInfo, Pageable pageable){
		Page<UserInfoDto> pageUserInfo = cmUserRepository.pageUserInfo(searchUserInfo, pageable);
		
		if(pageUserInfo != null && !pageUserInfo.isEmpty()) {
			int num = pageUserInfo.getNumber() * pageUserInfo.getSize() + 1;
			for(UserInfoDto userInfoDto : pageUserInfo.getContent()) {
				userInfoDto.setNum(num++);
				userInfoDto.setCmRoleList(cmRoleRepository.findByUserSeq(userInfoDto.getUserSeq()));
			}
		}
		
		return pageUserInfo;
	}

	/**
	 * 사용자 정보 편집
	 * - 이미 있는 사용자를 대상으로 한다.
	 * 
	 * @param saveUserInfo
	 * @param userSeq
	 */
	public boolean editUserInfo(SaveUserInfo saveUserInfo, Long updatorSeq) {
		
		CmUser cmUser = cmUserRepository.getById(saveUserInfo.getUserSeq());		
		if(cmUser != null) {
			cmUser.setLoginId(saveUserInfo.getLoginId());
			cmUser.setUserNm(saveUserInfo.getUserNm());
			cmUser.setUserTelNo(saveUserInfo.getUserTelNo());
			cmUser.setLoginFailCnt(saveUserInfo.getLoginFailCnt());
			cmUser.setUserStatCd(saveUserInfo.getUserStatCd());
			
			cmUser.setDelYn(Yn.N);
			cmUser.setUpdatorSeq(updatorSeq);
			cmUser.setUpdDt(LocalDateTime.now());
			cmUserRepository.save(cmUser);
			
			// 일단 모든 역할을 제거한다.
			List<CmUserRoleMap> cmUserRoleMapList = cmUserRoleMapRepository.findByUserSeqAndDelYn(saveUserInfo.getUserSeq(), Yn.N);
			if(cmUserRoleMapList != null && !cmUserRoleMapList.isEmpty()) {
				for(CmUserRoleMap cmUserRoleMap : cmUserRoleMapList) {
					cmUserRoleMap.setDelYn(Yn.Y);
					cmUserRoleMap.setUpdatorSeq(updatorSeq);
					cmUserRoleMap.setUpdDt(LocalDateTime.now());
				}
				cmUserRoleMapRepository.saveAll(cmUserRoleMapList);
			}
			
			// 역할을 찾아 저장한다.
			if(saveUserInfo.getRoleCd() != null && !saveUserInfo.getRoleCd().isEmpty()) {
				int prntOrd = 1;
				for(String roleCd : saveUserInfo.getRoleCd()) {
					List<CmRole> cmRoleList = cmRoleRepository.findByRoleCdAndDelYn(RoleCd.valueOf(roleCd), Yn.N);
					if(cmRoleList != null && !cmRoleList.isEmpty()) {
						for(CmRole cmRole : cmRoleList) {
							CmUserRoleMap cmUserRoleMap = new CmUserRoleMap();
							cmUserRoleMap.setUserSeq(saveUserInfo.getUserSeq());
							cmUserRoleMap.setRoleSeq(cmRole.getRoleSeq());
							cmUserRoleMap.setPrntOrd(prntOrd++);
							
							cmUserRoleMap.setDelYn(Yn.N);
							cmUserRoleMap.setCreatorSeq(updatorSeq);
							cmUserRoleMap.setCreDt(LocalDateTime.now());
							cmUserRoleMap.setUpdatorSeq(updatorSeq);
							cmUserRoleMap.setUpdDt(LocalDateTime.now());
							
							cmUserRoleMapRepository.save(cmUserRoleMap);
						}
					}
				}
			}
			return true;
		}
		
		return false;
	}
}
