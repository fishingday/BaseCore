package kr.co.basedevice.corebase.service.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.cm.CmRoleMenuMap;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.dto.system.MenuInfoDto;
import kr.co.basedevice.corebase.dto.system.ParentMenuDto;
import kr.co.basedevice.corebase.dto.system.SaveMenuInfo;
import kr.co.basedevice.corebase.repository.cm.CmMenuRepository;
import kr.co.basedevice.corebase.repository.cm.CmRoleMenuMapRepository;
import kr.co.basedevice.corebase.repository.cm.CmRoleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MenuService {
	
	final private CmMenuRepository cmMenuRepository;
	final private CmRoleRepository cmRoleRepository;
	final private CmRoleMenuMapRepository cmRoleMenuMapRepository;
	
	/**
	 * 메뉴 조회는 모든 메뉴를 대상으로 하지만, 
	 * 검색 결과는 LeafMenu를 도출한 후
	 * 상위 메뉴를 재구성하면 목록을 만든다.  
	 * 
	 * @param searchMenuInfo
	 * @return
	 */
	@Cacheable(value = "MENU", key="'PRNT'")
	public List<CmMenu> findByMenuList() {
		
		// 당연히 삭제된 것과 대시보드를 비롯하여 표시되지 않는 메뉴는 제외
		List<CmMenu> cmMenuList = cmMenuRepository.findByPrntYnAndDelYnOrderByPrntOrdAsc(Yn.Y, Yn.N);
		
		return cmMenuList;
	}

	/**
	 * 메뉴 삭제
	 * - 하위 메뉴가 없어야 삭제 가능
	 * 
	 * @param menuSeq
	 * @return
	 */
	@Caching(evict = {
        @CacheEvict(value = "MENU", key="'PRNT'"),
		@CacheEvict(value = "MENU", key="'ALL'"),
		@CacheEvict(value = "AUTH_MENU", key="'MenuInfo'"),
		@CacheEvict(value = "AUTH_MENU", key="'Parent'")
	})
	public boolean removeMenu(Long menuSeq) {
		
		// 하위 메뉴 건수를 조회한다.
		Long cnt = cmMenuRepository.countByUpMenuSeqAndDelYn(menuSeq, Yn.N);
		
		if(cnt > 0L) {
			return false;
		}else {
			CmMenu cmMenu = cmMenuRepository.getReferenceById(menuSeq);
			
			cmMenu.setDelYn(Yn.Y);			
			cmMenuRepository.save(cmMenu);
			
			return true;
		}		
	}

	/**
	 * 메뉴 저장
	 * 
	 * @param cmMenu
	 * @return
	 */
	@Caching(evict = {
        @CacheEvict(value = "MENU", key="'PRNT'"),
		@CacheEvict(value = "MENU", key="'ALL'"),
		@CacheEvict(value = "AUTH_MENU", key="'MenuInfo'"),
		@CacheEvict(value = "AUTH_MENU", key="'Parent'")
	})
	public boolean saveCmMenu(SaveMenuInfo saveMenuInfo, Long updatorSeq) {
		
		CmMenu cmMenu = null;
		if(ObjectUtils.isEmpty(saveMenuInfo.getMenuSeq())) {
			cmMenu = new CmMenu();
		}else {
			cmMenu = cmMenuRepository.getReferenceById(saveMenuInfo.getMenuSeq());
			List<CmRoleMenuMap> cmRoleMenuMapList = cmRoleMenuMapRepository.findByMenuSeqAndDelYn(saveMenuInfo.getMenuSeq(), Yn.N);
			if(cmRoleMenuMapList != null && !cmRoleMenuMapList.isEmpty()) {
				for(CmRoleMenuMap cmRoleMenuMap : cmRoleMenuMapList) {
					cmRoleMenuMap.setDelYn(Yn.Y);
				}
				cmRoleMenuMapRepository.saveAll(cmRoleMenuMapList);
			}			
		}
		
		cmMenu.setUpMenuSeq(saveMenuInfo.getUpMenuSeq());
		cmMenu.setMenuNm(saveMenuInfo.getMenuNm());
		cmMenu.setMenuPath(saveMenuInfo.getMenuPath());
		cmMenu.setMenuDesc(saveMenuInfo.getMenuDesc());
		cmMenu.setPrntOrd(saveMenuInfo.getPrntOrd());
		cmMenu.setIConInfo(saveMenuInfo.getIConInfo());
		cmMenu.setPrntYn(saveMenuInfo.getPrntYn());
		cmMenu.setCmScrenYn(saveMenuInfo.getCmScrenYn());
		cmMenu.setDelYn(Yn.N);
		
		CmMenu saveMenu = cmMenuRepository.save(cmMenu);
		
		if(saveMenuInfo.getRoleSeqList() != null && !saveMenuInfo.getRoleSeqList().isEmpty()) {
		  for(Long roleSeq : saveMenuInfo.getRoleSeqList()) {
			  CmRoleMenuMap cmRoleMenuMap = new CmRoleMenuMap();
			  cmRoleMenuMap.setMenuSeq(saveMenu.getMenuSeq());
			  cmRoleMenuMap.setRoleSeq(roleSeq);
			  cmRoleMenuMap.setDelYn(Yn.N);
			  
			  cmRoleMenuMapRepository.save(cmRoleMenuMap);
		  }	
		}
		
		return true;
	}

	/**
	 * 메뉴 조회
	 * 
	 * @param searchMenu
	 * @return
	 */
	@Cacheable(value="AUTH_MENU", key="'MenuInfo'")
	public List<MenuInfoDto> findByRoleSeq() {
		
		List<MenuInfoDto> menuInfoDtoList = cmMenuRepository.findBySearch();
		
		if(menuInfoDtoList != null && !menuInfoDtoList.isEmpty()) {
			for(MenuInfoDto menuInfoDto : menuInfoDtoList) {
				// 역할 코드 
				List<CmRole> cmRoleList = cmRoleRepository.findByMenuSeq(menuInfoDto.getMenuSeq());
				menuInfoDto.setCmRoleList(cmRoleList);
				
				// 하위 메뉴 여부
				Long cnt = cmMenuRepository.countByUpMenuSeqAndDelYn(menuInfoDto.getMenuSeq(), Yn.N);
				if(cnt.longValue() > 0L) {
					menuInfoDto.setLeaf(false);
				}else{
					menuInfoDto.setLeaf(true);
				}
			}			
		}
		
		return menuInfoDtoList;
	}

	/**
	 * 하위 메뉴가 없는 메뉴 목록
	 * 
	 * @return
	 */
	@Cacheable(value = "AUTH_MENU", key="'Parent'")
	public List<ParentMenuDto> findByParentMenuList() {
		
		// 하위 메뉴가 있는 것만 조회해서...
		List<ParentMenuDto> menuInfoDtoList = cmMenuRepository.findByParentMenuList();
		
		if(menuInfoDtoList != null && !menuInfoDtoList.isEmpty()) {
			Map<Long, ParentMenuDto> parentMenuDtoMap = new HashMap<>(menuInfoDtoList.size());
			// 등록하기
			for(ParentMenuDto parentMenuDto : menuInfoDtoList) {
				parentMenuDtoMap.put(parentMenuDto.getMenuSeq(), parentMenuDto);
			}
			// 부모 찾기
			for(ParentMenuDto parentMenuDto : menuInfoDtoList) {
				if(parentMenuDto.getUpMenuSeq() == null) {
					continue;
				}
				
				ParentMenuDto upMenu = parentMenuDtoMap.get(parentMenuDto.getUpMenuSeq());
				parentMenuDto.setUpMenu(upMenu);
			}
			// 레벨 등록
			for(ParentMenuDto parentMenuDto : menuInfoDtoList) {
				if(parentMenuDto.getUpMenu() == null) {
					parentMenuDto.setLevel(1);
					continue;
				}
				ParentMenuDto upMenu = parentMenuDto.getUpMenu();
				int idx = 2;
				while(true) {
					if(upMenu.getUpMenuSeq() == null) {
						parentMenuDto.setLevel(idx);
						break;
					}else{
						upMenu = upMenu.getUpMenu();
					}
					idx++;
				}
			}
			Collections.sort(menuInfoDtoList);
		}
		
		return menuInfoDtoList;
	}

	/**
	 * 잃어버린 부모를 찾아 주고
	 * 정렬하여 목록을 출력
	 * 
	 * @param cmMenuList
	 * @return
	 */
	public List<ParentMenuDto> makeParentMenuList(List<CmMenu> cmMenuList) {
		List<ParentMenuDto> parentMenuDtoList = new ArrayList<>();
		
		if(cmMenuList != null && !cmMenuList.isEmpty()) {
			Map<Long, ParentMenuDto> mapUpMenu = this.getParnetMenuMap();
			
			// 잃어버린 부모를 찾아 줌
			for(CmMenu cmMenu : cmMenuList) {
				ParentMenuDto parentMenuDto = ParentMenuDto.setCmMenu(cmMenu);
				parentMenuDtoList.add(parentMenuDto);
				if(parentMenuDto.getUpMenuSeq() != null) {
					parentMenuDto.setUpMenu(mapUpMenu.get(parentMenuDto.getUpMenuSeq()));
				}
			}
			
			// 레벨 등록
			for(ParentMenuDto parentMenuDto : parentMenuDtoList) {
				if(parentMenuDto.getUpMenu() == null) {
					parentMenuDto.setLevel(1);
					continue;
				}
				ParentMenuDto upMenu = parentMenuDto.getUpMenu();
				int idx = 2;
				while(true) {
					if(upMenu.getUpMenuSeq() == null) {
						parentMenuDto.setLevel(idx);
						break;
					}else{
						upMenu = upMenu.getUpMenu();
					}
					idx++;
				}
			}
			Collections.sort(parentMenuDtoList);
		}
		
		return parentMenuDtoList;
	}
	
	private Map<Long, ParentMenuDto> getParnetMenuMap(){
		Map<Long, ParentMenuDto> parnetMenuMap = new HashMap<>();
		List<ParentMenuDto> menuInfoDtoList = this.findByParentMenuList(); // 부코들의 계층관계가 완성된 형태
		
		for(ParentMenuDto parentMenuDto : menuInfoDtoList) {
			parnetMenuMap.put(parentMenuDto.getMenuSeq(), parentMenuDto);
		}
		
		return parnetMenuMap;
	}

}
