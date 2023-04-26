package kr.co.basedevice.corebase.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.cm.CmMenuDtl;
import kr.co.basedevice.corebase.repository.cm.CmMenuRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SecurityResourceService {

	private CmMenuRepository cmMenuRepository;

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
                        result.put(new AntPathRequestMatcher(menu.getMenuPath()), configAttributeList);
                    });
                }
        );
        return result;
    }
}