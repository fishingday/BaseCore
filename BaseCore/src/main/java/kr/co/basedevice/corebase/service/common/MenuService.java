package kr.co.basedevice.corebase.service.common;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.cm.CmRoleMenuMap;
import kr.co.basedevice.corebase.domain.cm.CmRoleMenuMapId;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.repository.cm.CmMenuRepository;
import kr.co.basedevice.corebase.repository.cm.CmRoleMenuMapRepository;
import kr.co.basedevice.corebase.repository.cm.CmRoleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MenuService {
	
	private CmMenuRepository cmMenuRepository;
	private CmRoleRepository cmRoleRepository;
	private CmRoleMenuMapRepository cmRoleMenuMapRepository;
	
	/**
	 * 메뉴 조회는 모든 메뉴를 대상으로 하지만, 
	 * 검색 결과는 LeafMenu를 도출한 후
	 * 상위 메뉴를 재구성하면 목록을 만든다.  
	 * 
	 * @param searchMenuInfo
	 * @return
	 */
	public List<CmMenu> findByMenuList() {
		
		// 당연히 삭제된 것과 대시보를 비롯하여 표시되지 않는 메뉴는 제외
		List<CmMenu> cmMenuList = cmMenuRepository.findByPrntYnAndDelYnOrderByPrntOrdAsc(Yn.Y, Yn.N);
		
		return cmMenuList;
	}

	/**
	 * 메뉴 삭제
	 * - 하위 메뉴가 없어야 삭제 가능
	 * 
	 * @param menuSeq
	 * @param userSeq
	 * @return
	 */
	public boolean removeMenu(Long menuSeq, Long userSeq) {
		
		// 하위 메뉴 건수를 조회한다.
		Long cnt = cmMenuRepository.countByUpMenuSeqAndDelYn(menuSeq, Yn.N);
		
		if(cnt > 0L) {
			return false;
		}else {
			CmMenu cmMenu = cmMenuRepository.getById(menuSeq);
			
			cmMenu.setDelYn(Yn.N);
			cmMenu.setUpdatorSeq(userSeq);
			cmMenu.setUpdDt(LocalDateTime.now());
			
			cmMenuRepository.save(cmMenu);
			
			return true;
		}		
	}

	/**
	 * 메뉴 저장
	 * 
	 * @param cmMenu
	 * @param updatorSeq
	 * @return
	 */
	public CmMenu saveCmMenu(CmMenu cmMenu, Long updatorSeq) {

		cmMenu.setDelYn(Yn.N);
		cmMenu.setCreatorSeq(updatorSeq);
		cmMenu.setCreDt(LocalDateTime.now());
		cmMenu.setUpdatorSeq(updatorSeq);
		cmMenu.setUpdDt(LocalDateTime.now());
		
		return cmMenuRepository.save(cmMenu);
	}

	/**
	 * 메뉴 순서 변경
	 * 
	 * @param chgMenuSeq
	 * @param chgOrd
	 * @param tgtMenuSeq
	 * @param tgtOrd
	 * @param userSeq
	 * @return
	 */
	public boolean chgOrderMenu(Long chgMenuSeq, Integer chgOrd, Long tgtMenuSeq, Integer tgtOrd, Long updatorSeq) {
		CmMenu chgMenu = cmMenuRepository.getById(chgMenuSeq);
		chgMenu.setDelYn(Yn.N);
		chgMenu.setPrntOrd(chgOrd);
		chgMenu.setUpdatorSeq(updatorSeq);
		chgMenu.setUpdDt(LocalDateTime.now());
		
		cmMenuRepository.save(chgMenu);
		
		CmMenu tgtMenu = cmMenuRepository.getById(tgtMenuSeq);
		tgtMenu.setDelYn(Yn.N);
		tgtMenu.setPrntOrd(tgtOrd);
		tgtMenu.setUpdatorSeq(updatorSeq);
		tgtMenu.setUpdDt(LocalDateTime.now());
		
		cmMenuRepository.save(tgtMenu);
		
		return true;
	}

	/**
	 * 메뉴별 역할 목록
	 * 
	 * @param menuSeq
	 * @return
	 */
	public List<CmRole> findByRoleList(Long menuSeq) {		
		
		List<CmRole> cmRoleList = cmRoleRepository.findByMenuSeq(menuSeq);		
		
		return cmRoleList;
	}

	/**
	 * 메뉴별 역할 추가
	 * 
	 * @param menuSeq
	 * @param roleSeq
	 * @param userSeq
	 * @return
	 */
	public boolean addRole(Long menuSeq, Long roleSeq, Long updatorSeq) {
		CmRoleMenuMap cmRoleMenuMap = new CmRoleMenuMap();
		
		cmRoleMenuMap.setRoleSeq(roleSeq);
		cmRoleMenuMap.setMenuSeq(menuSeq);
		
		cmRoleMenuMap.setDelYn(Yn.N);
		cmRoleMenuMap.setCreatorSeq(updatorSeq);
		cmRoleMenuMap.setCreDt(LocalDateTime.now());
		cmRoleMenuMap.setUpdatorSeq(updatorSeq);
		cmRoleMenuMap.setUpdDt(LocalDateTime.now());
		
		cmRoleMenuMapRepository.save(cmRoleMenuMap);
		
		return true;
	}

	/**
	 * 메뉴별 역할 삭제
	 * 
	 * @param menuSeq
	 * @param roleSeq
	 * @param updatorSeq
	 * @return
	 */
	public boolean removeRole(Long menuSeq, Long roleSeq, Long updatorSeq) {
		CmRoleMenuMapId cmRoleMenuMapId = new CmRoleMenuMapId();
		cmRoleMenuMapId.setRoleSeq(roleSeq);
		cmRoleMenuMapId.setMenuSeq(menuSeq);
		
		CmRoleMenuMap cmRoleMenuMap = cmRoleMenuMapRepository.getById(cmRoleMenuMapId);
		
		cmRoleMenuMap.setDelYn(Yn.Y);
		cmRoleMenuMap.setUpdatorSeq(updatorSeq);
		cmRoleMenuMap.setUpdDt(LocalDateTime.now());
		
		cmRoleMenuMapRepository.save(cmRoleMenuMap);
		
		return true;
	}

}
