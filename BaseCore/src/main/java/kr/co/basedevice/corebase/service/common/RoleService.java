package kr.co.basedevice.corebase.service.common;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.cm.CmRoleMenuMap;
import kr.co.basedevice.corebase.domain.cm.CmRoleMenuMapId;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.cm.CmUserRoleMap;
import kr.co.basedevice.corebase.domain.cm.CmUserRoleMapId;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.dto.system.ChooseMenusRole;
import kr.co.basedevice.corebase.dto.system.ChooseUsersRole;
import kr.co.basedevice.corebase.repository.cm.CmMenuRepository;
import kr.co.basedevice.corebase.repository.cm.CmRoleMenuMapRepository;
import kr.co.basedevice.corebase.repository.cm.CmRoleRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserRoleMapRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class RoleService {

	final private CmRoleRepository cmRoleRepository;
	final private CmUserRepository cmUserRepository;
	final private CmMenuRepository cmMenuRepository;
	final private CmUserRoleMapRepository cmUserRoleMapRepository;
	final private CmRoleMenuMapRepository cmRoleMenuMapRepository;
	
	/**
	 *  역할 단건 조회
	 *  
	 * @param roleSeq
	 * @return
	 */
	public CmRole findById(Long roleSeq){
		Optional<CmRole> cmRole = cmRoleRepository.findById(roleSeq);
		return cmRole.get();
	}
	
	
	/**
	 * 역할 저장
	 * 
	 * @param cmRole
	 * @param operatorSeq
	 * @return
	 */
	public CmRole saveCmRole(CmRole cmRole){
		
		cmRole.setDelYn(Yn.N);
		
		return cmRoleRepository.save(cmRole);
	}
	
	
	/**
	 *  역할 삭제
	 *  - 역할을 삭제할 때는 사용자와 메뉴 맵핑 정보도 함께 삭제
	 * 
	 * @param roleSeq
	 * @param operatorSeq
	 * @return
	 */
	public CmRole removeCmRole(Long roleSeq){
		
		// 역할 별 메뉴 맵핑 삭제
		List<CmRoleMenuMap> cmRoleMenuMapList = cmRoleMenuMapRepository.findByRoleSeqAndDelYn(roleSeq, Yn.N);
		if(cmRoleMenuMapList != null && !cmRoleMenuMapList.isEmpty()) {
			for(CmRoleMenuMap cmRoleMenuMap : cmRoleMenuMapList) {
				cmRoleMenuMap.setDelYn(Yn.Y);
			}
			cmRoleMenuMapRepository.saveAll(cmRoleMenuMapList);
		}
		
		// 역할 별 사용자 맵핑 삭제
		List<CmUserRoleMap> cmUserRoleMapList = cmUserRoleMapRepository.findByRoleSeqAndDelYn(roleSeq, Yn.N);
		if(cmUserRoleMapList != null && !cmUserRoleMapList.isEmpty()) {
			for(CmUserRoleMap cmUserRoleMap : cmUserRoleMapList) {
				cmUserRoleMap.setDelYn(Yn.Y);
			}
			cmUserRoleMapRepository.saveAll(cmUserRoleMapList);
		}
		
		// 역할 삭제
		CmRole cmRole = cmRoleRepository.getById(roleSeq);
		cmRole.setDelYn(Yn.Y);
		return cmRoleRepository.save(cmRole);
	}
	
	/**
	 * 역할 별 사용자 목록
	 * 
	 * @param roleSeq
	 * @param pageable
	 * @return
	 */
	public List<CmUser> findByCmUser(Long roleSeq){
		List<CmUser> cmUserList = cmUserRepository.findByRoleSeq(roleSeq);
		
		return cmUserList;
	}

	/**
	 * 특정 역할 제외 사용자 목록
	 * 
	 * @param roleSeq
	 * @return
	 */
	public List<CmUser> findByExcludeCmUser(Long roleSeq) {
		List<CmUser> cmUserList = cmUserRepository.findByExcludeRoleSeq(roleSeq);
		
		return cmUserList;
	}
	
	/**
	 *  역할 별 메뉴 목록
	 * 
	 * @param roleSeq
	 * @param pageable
	 * @return
	 */
	public List<CmMenu> findByCmMenu(Long roleSeq){
		List<CmMenu> cmMenuList = cmMenuRepository.findByRoleSeq(roleSeq);
		
		return cmMenuList;
	}

	/**
	 * 특정 역할 제외 메뉴 목록
	 * 
	 * @param roleSeq
	 * @return
	 */
	public List<CmMenu> findByExcludeCmMenu(Long roleSeq) {
		List<CmMenu> cmMenuList = cmMenuRepository.findByExcludeRoleSeq(roleSeq);
		
		return cmMenuList;
	}
	
	/**
	 *  역할별 사용자 추가
	 * 
	 * @param roleSeq
	 * @param userSeqList
	 * @param operatorSeq
	 * @return
	 */
	public int addUsers(ChooseUsersRole chooseUsers){
		if(chooseUsers.getUserSeqList() != null && !chooseUsers.getUserSeqList().isEmpty()) {
			int inc = 0;
			for(Long userSeq : chooseUsers.getUserSeqList()) {
				CmUserRoleMapId cmUserRoleMapId = new CmUserRoleMapId();
				cmUserRoleMapId.setUserSeq(userSeq);
				cmUserRoleMapId.setRoleSeq(chooseUsers.getRoleSeq());
				CmUserRoleMap cmUserRoleMap = cmUserRoleMapRepository.getById(cmUserRoleMapId);
				
				if(cmUserRoleMap == null) {
					cmUserRoleMap = new CmUserRoleMap();
					cmUserRoleMap.setUserSeq(userSeq);
					cmUserRoleMap.setRoleSeq(chooseUsers.getRoleSeq());
					cmUserRoleMap.setPrntOrd(9); // 정확한 순서는 사용자가 직접... 여기서는 부여만!
				}
				cmUserRoleMap.setDelYn(Yn.N);
				
				cmUserRoleMapRepository.save(cmUserRoleMap);				
				inc++;
			}
			return inc;
		}
		return 0;
	}
	
	/**
	 *  역할 별 사용자 제거
	 * 
	 * @param roleSeq
	 * @param userSeqList
	 * @param operatorSeq
	 * @return
	 */
	public int removeUsers(ChooseUsersRole chooseUsers){		
		if(chooseUsers.getUserSeqList() != null && !chooseUsers.getUserSeqList().isEmpty()) {
			int inc = 0;
			for(Long userSeq : chooseUsers.getUserSeqList()) {
				CmUserRoleMapId cmUserRoleMapId = new CmUserRoleMapId();
				cmUserRoleMapId.setUserSeq(userSeq);
				cmUserRoleMapId.setRoleSeq(chooseUsers.getRoleSeq());
				CmUserRoleMap cmUserRoleMap = cmUserRoleMapRepository.getById(cmUserRoleMapId);
				
				if(cmUserRoleMap == null) {
					continue;
				}
				cmUserRoleMap.setDelYn(Yn.Y);
				
				cmUserRoleMapRepository.save(cmUserRoleMap);				
				inc++;
			}
			return inc;
		}
		return 0;
		
	}
	
	/**
	 *  역할 별 메뉴 추가
	 * 
	 * @param roleSeq
	 * @param menuSeqList
	 * @param operatorSeq
	 * @return
	 */
	public int addMenus(ChooseMenusRole chooseMenus){		
		if(chooseMenus.getMenuSeqList() != null && !chooseMenus.getMenuSeqList().isEmpty()) {
			int inc = 0;
			for(Long menuSeq : chooseMenus.getMenuSeqList()) {
				CmRoleMenuMapId cmRoleMenuMapId = new CmRoleMenuMapId();
				cmRoleMenuMapId.setRoleSeq(chooseMenus.getRoleSeq());
				cmRoleMenuMapId.setMenuSeq(menuSeq);
				CmRoleMenuMap cmRoleMenuMap = cmRoleMenuMapRepository.getById(cmRoleMenuMapId);
				
				if(cmRoleMenuMap == null) {
					cmRoleMenuMap = new CmRoleMenuMap();
					cmRoleMenuMap.setRoleSeq(chooseMenus.getRoleSeq());
					cmRoleMenuMap.setMenuSeq(menuSeq);
				}
				cmRoleMenuMap.setDelYn(Yn.N);
				
				cmRoleMenuMapRepository.save(cmRoleMenuMap);				
				inc++;
			}
			return inc;
		}
		return 0;
	}
	
	/**
	 *  역할 별 메뉴 제거
	 * 
	 * @param roleSeq
	 * @param menuSeqList
	 * @param operatorSeq
	 * @return
	 */
	public int removeMenus(ChooseMenusRole chooseMenus){		
		if(chooseMenus.getMenuSeqList() != null && !chooseMenus.getMenuSeqList().isEmpty()) {
			int inc = 0;
			for(Long menuSeq : chooseMenus.getMenuSeqList()) {
				CmRoleMenuMapId cmRoleMenuMapId = new CmRoleMenuMapId();
				cmRoleMenuMapId.setRoleSeq(chooseMenus.getRoleSeq());
				cmRoleMenuMapId.setMenuSeq(menuSeq);
				CmRoleMenuMap cmRoleMenuMap = cmRoleMenuMapRepository.getById(cmRoleMenuMapId);
				
				if(cmRoleMenuMap == null) {
					continue;
				}
				cmRoleMenuMap.setDelYn(Yn.Y);
				
				cmRoleMenuMapRepository.save(cmRoleMenuMap);				
				inc++;
			}
			return inc;
		}
		return 0;
	}
	

	/**
	 *  역할 목록 조회
	 * 
	 * @param searchRoleDto
	 * @return
	 */
	public List<CmRole> findByRoleList(){
		return cmRoleRepository.findByDelYn(Yn.N);
	}
	
}
