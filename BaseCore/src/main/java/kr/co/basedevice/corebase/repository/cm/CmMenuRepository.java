package kr.co.basedevice.corebase.repository.cm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.repository.cm.querydsl.CmMenuRepositoryQuerydsl;

public interface CmMenuRepository  extends JpaRepository<CmMenu, Long>, CmMenuRepositoryQuerydsl{

	CmMenu findByMenuSeqAndDelYn(Long upMenuSeq, Yn delyn);

	List<CmMenu> findByDelYn(Yn delyn);

	List<CmMenu> findByPrntYnAndDelYnOrderByPrntOrdAsc(Yn prntYn, Yn delYn);

	Long countByUpMenuSeqAndDelYn(Long menuSeq, Yn delyn);

}
