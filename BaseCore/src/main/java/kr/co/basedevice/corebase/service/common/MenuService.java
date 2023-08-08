package kr.co.basedevice.corebase.service.common;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.repository.cm.CmMenuRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MenuService {
	
	private CmMenuRepository cmMenuRepository;
	
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

}
