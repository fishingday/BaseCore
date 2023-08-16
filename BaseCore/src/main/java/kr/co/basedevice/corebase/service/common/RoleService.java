package kr.co.basedevice.corebase.service.common;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.cm.CmRoleMenuMap;
import kr.co.basedevice.corebase.domain.cm.CmRoleMenuMapId;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.cm.CmUserRoleMap;
import kr.co.basedevice.corebase.domain.cm.CmUserRoleMapId;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.repository.cm.CmRoleMenuMapRepository;
import kr.co.basedevice.corebase.repository.cm.CmRoleRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserRoleMapRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class RoleService {

	final private CmRoleRepository cmRoleRepository;
	final private CmUserRoleMapRepository cmUserRoleMapRepository;
	final private CmRoleMenuMapRepository cmRoleMenuMapRepository;
	
	/**
	 *  역할 단건 조회
	 *  
	 * @param roleSeq
	 * @return
	 */
	public CmRole findById(Long roleSeq){
		// proxy를 사용하지 않도록
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
	public CmRole saveCmRole(CmRole cmRole, Long operatorSeq){
		
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
	public CmRole removeCmRole(Long roleSeq, Long operatorSeq){
		
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
	public Page<CmUser> findByCmUser(Long roleSeq, Pageable pageable){
		// TODO
		
		return null;
	}
	
	/**
	 *  역할 별 메뉴 목록
	 * 
	 * @param roleSeq
	 * @param pageable
	 * @return
	 */
	public Page<CmMenu> findByCmMenu(Long roleSeq, Pageable pageable){
		// TODO
		
		return null;
	}
	
	/**
	 *  역할별 사용자 추가
	 * 
	 * @param roleSeq
	 * @param userSeqList
	 * @param operatorSeq
	 * @return
	 */
	public int addUserSeq(Long roleSeq, Collection<Long> userSeqList, Long operatorSeq){
		if(userSeqList != null && !userSeqList.isEmpty()) {
			int inc = 0;
			for(Long userSeq : userSeqList) {
				CmUserRoleMapId cmUserRoleMapId = new CmUserRoleMapId();
				cmUserRoleMapId.setUserSeq(userSeq);
				cmUserRoleMapId.setRoleSeq(roleSeq);
				CmUserRoleMap cmUserRoleMap = cmUserRoleMapRepository.getById(cmUserRoleMapId);
				
				if(cmUserRoleMap == null) {
					cmUserRoleMap = new CmUserRoleMap();
					cmUserRoleMap.setUserSeq(userSeq);
					cmUserRoleMap.setRoleSeq(roleSeq);
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
	public int removeUserSeq(Long roleSeq, Collection<Long> userSeqList, Long operatorSeq){		
		if(userSeqList != null && !userSeqList.isEmpty()) {
			int inc = 0;
			for(Long userSeq : userSeqList) {
				CmUserRoleMapId cmUserRoleMapId = new CmUserRoleMapId();
				cmUserRoleMapId.setUserSeq(userSeq);
				cmUserRoleMapId.setRoleSeq(roleSeq);
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
	public int addMenuSeq(Long roleSeq, Collection<Long> menuSeqList, Long operatorSeq){		
		if(menuSeqList != null && !menuSeqList.isEmpty()) {
			int inc = 0;
			for(Long menuSeq : menuSeqList) {
				CmRoleMenuMapId cmRoleMenuMapId = new CmRoleMenuMapId();
				cmRoleMenuMapId.setRoleSeq(roleSeq);
				cmRoleMenuMapId.setMenuSeq(menuSeq);
				CmRoleMenuMap cmRoleMenuMap = cmRoleMenuMapRepository.getById(cmRoleMenuMapId);
				
				if(cmRoleMenuMap == null) {
					cmRoleMenuMap = new CmRoleMenuMap();
					cmRoleMenuMap.setRoleSeq(roleSeq);
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
	public int removeMenuSeq(Long roleSeq, Collection<Long> menuSeqList, Long operatorSeq){		
		if(menuSeqList != null && !menuSeqList.isEmpty()) {
			int inc = 0;
			for(Long menuSeq : menuSeqList) {
				CmRoleMenuMapId cmRoleMenuMapId = new CmRoleMenuMapId();
				cmRoleMenuMapId.setRoleSeq(roleSeq);
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
