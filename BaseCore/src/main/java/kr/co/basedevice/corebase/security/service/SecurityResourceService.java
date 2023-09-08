package kr.co.basedevice.corebase.security.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.ObjectUtils;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.cm.CmMenuDtl;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.dto.common.MenuDto;
import kr.co.basedevice.corebase.repository.cm.CmMenuRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SecurityResourceService {

	private CmMenuRepository cmMenuRepository;

	/**
	 * Spring Security 초기화에 사용할 리소스 정보 제굥용
	 * 
	 * @return
	 */
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {

    	LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        
        // 상세 메뉴 목록을 조회해서 정렬하고...
        List<CmMenuDtl> cmMenuDtlList = cmMenuRepository.findAllMenuDtl();
    	Collections.sort( cmMenuDtlList, (o1,o2) -> o2.getDepth() - o1.getDepth() );
    	cmMenuDtlList.forEach(menuDtl ->
                {
                    List<ConfigAttribute> configAttributeList = new ArrayList<>();
                    menuDtl.getCmMenuDtlRoleMapList().forEach(ro -> {
                        configAttributeList.add(new SecurityConfig(ro.getCmRole().getRoleCd().toString()));
                        result.put(new AntPathRequestMatcher(menuDtl.getCmMenu().getMenuPath() + menuDtl.getMenuDtlPath()), configAttributeList);
                    });
                }
        );
        
        // 메인 메뉴 목록을 조회해서 정렬하고...
    	List<CmMenu> cmMenuList = cmMenuRepository.findAllMenu();    	
    	Collections.sort( cmMenuList, (o1,o2) -> o2.getDepth() - o1.getDepth() );
    	cmMenuList.forEach(menu ->
                {
                    List<ConfigAttribute> configAttributeList = new ArrayList<>();
                    menu.getCmRoleMenuMapList().forEach(ro -> {
                        configAttributeList.add(new SecurityConfig(ro.getCmRole().getRoleCd().toString()));
                        
                        // 한번은 그냥 
                        result.put(new AntPathRequestMatcher(menu.getMenuPath()), configAttributeList);
                        
                        // 또 한번은 바로 아래 것 까지만...
                        result.put(new AntPathRequestMatcher(menu.getMenuPath() + "/*"), configAttributeList);
                    });
                }
        );
        return result;
    }

    /**
     * 모든 메뉴 정보조회
     * - 세션 추적용 Interceptor에서 사용할 메뉴 정보
     * 
     * @return
     */
	public LinkedHashMap<RequestMatcher, MenuDto> getMenuMap() {
		LinkedHashMap<RequestMatcher, MenuDto> result = new LinkedHashMap<>();
		Map<Long, MenuDto> menuMap = new HashMap<>(); 
		
		List<CmMenu> cmMenuList = cmMenuRepository.findByDelYn(Yn.N); // 일단은 모든 메뉴를...
		
		// 옛날 옛날 어느 프로그래머가 모든 메뉴의 부모를 찾아주기 위해 
		// 메뉴의 정보를 모으기 시작 했답니다...
		for(CmMenu cmMenu : cmMenuList) {
			menuMap.put(cmMenu.getMenuSeq(), new MenuDto(cmMenu));
		}
		
		for(CmMenu cmMenu : cmMenuList) {
			// 여기 한 메뉴가 있었습니다.
			MenuDto menu = menuMap.get(cmMenu.getMenuSeq());
			
			// 메뉴야... 너는 부모가 있니? 부모는 찾았고?
			if(!ObjectUtils.isEmpty(menu.getUpMenuSeq()) && menu.getUpMenu() == null) {
				// 메뉴에게 부모를 찾아 주었습니다.
				MenuDto parentMenu = menuMap.get(cmMenu.getUpMenuSeq());
				
				if(parentMenu != null) {
					menu.setUpMenu(parentMenu);					
					// 부모 메뉴의 가족으로 등록도 하고...
					parentMenu.addSubMenu(menu);	
				}
			}

			// 그런데 메뉴야? ... 너는 주소가 있니?
			if(!ObjectUtils.isEmpty(cmMenu.getMenuPath())) {	
				// 한번은 그냥 
				result.put(new AntPathRequestMatcher(cmMenu.getMenuPath()), menu);                
                // 또 한번은 바로 아래 것 까지만...
                result.put(new AntPathRequestMatcher(cmMenu.getMenuPath() + "/*"), menu);
			}
		}
		return result;
	}
}