package kr.co.basedevice.corebase.repository.cm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.repository.cm.querydsl.CmMenuRepositoryQuerydsl;

public interface CmMenuRepository  extends JpaRepository<CmMenu, Long>, CmMenuRepositoryQuerydsl{

	/**
	 * 사용가능한 모든 메뉴 목록
	 * 
	 * @param delyn
	 * @return
	 */
	List<CmMenu> findByDelYn(Yn delyn);

	/**
	 * 화면 표시 메뉴 목록
	 * 
	 * @param prntYn
	 * @param delYn
	 * @return
	 */
	List<CmMenu> findByPrntYnAndDelYnOrderByPrntOrdAsc(Yn prntYn, Yn delYn);

	/**
	 * 상위메뉴 별 하위메뉴 수
	 * 
	 * @param menuSeq
	 * @param delyn
	 * @return
	 */
	Long countByUpMenuSeqAndDelYn(Long menuSeq, Yn delyn);

	/**
	 * 역할별 메뉴 목록
	 * 
	 * @param roleSeq
	 * @return
	 */
	@Query("select m from CmMenu m inner join CmRoleMenuMap n on (m.menuSeq = n.menuSeq) where m.delYn = 'N' and n.delYn = 'N' and length(m.menuPath) > 0 and n.roleSeq = ?1  order by m.menuSeq asc")
	List<CmMenu> findByRoleSeq(Long roleSeq);

	/**
	 * 특정 역할 제외 메뉴 목록
	 * 
	 * @param roleSeq
	 * @return
	 */
	@Query("select m from CmMenu m inner join CmRoleMenuMap n on (m.menuSeq = n.menuSeq) where m.delYn = 'N' and n.delYn = 'N' and length(m.menuPath) > 0 and n.roleSeq != ?1  order by m.menuSeq asc")
	List<CmMenu> findByExcludeRoleSeq(Long roleSeq);

}
