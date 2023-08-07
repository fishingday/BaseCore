package kr.co.basedevice.corebase.service.common;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.cm.CmCdDtl;
import kr.co.basedevice.corebase.domain.cm.CmCdGrp;
import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.repository.cm.CmCdDtlRepository;
import kr.co.basedevice.corebase.repository.cm.CmCdGrpRepository;
import kr.co.basedevice.corebase.repository.cm.CmRoleRepository;
import kr.co.basedevice.corebase.search.common.SearchCodeGrp;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CommonService {

	private final CmRoleRepository cmRoleRepository;
	private CmCdGrpRepository cmCdGrpRepository;
	private final CmCdDtlRepository cmCdDtlRepository;
	
	/**
	 * 역할 목록 조회
	 * 
	 * @return
	 */
	public List<CmRole> getCmRoleList(){
		List<CmRole> cmRoleLsit =  cmRoleRepository.findByDelYn(Yn.N);
		
		return cmRoleLsit;
	}

	/**
	 * 코드 상세 목록 조회
	 * 
	 * @param grpCd
	 * @return
	 */
	public List<CmCdDtl> getCmCdDtlList(String grpCd){
		List<CmCdDtl> cmCdDtlList = cmCdDtlRepository.findByGrpCd(grpCd);
		
		return cmCdDtlList;
	}

	/**
	 * 코드 그룹 목록
	 * 
	 * @param searchCodeGrp
	 * @return
	 */
	public List<CmCdGrp> findBySearch(SearchCodeGrp searchCodeGrp) {
		List<CmCdGrp> cmCdGrpList  = cmCdGrpRepository.findBySearch(searchCodeGrp);
		return cmCdGrpList;
	}

	/**
	 * 코드 그룹 상세 조회
	 * 
	 * @param grpCd
	 * @return
	 */
	public CmCdGrp findCmCdGrpById(String grpCd) {
		Optional<CmCdGrp> CmCdGrp = cmCdGrpRepository.findById(grpCd);
		
		if(CmCdGrp.isEmpty()) {
			return null;
		}else {
			return CmCdGrp.get();
		}
	}

	/**
	 * 코드 그룹 저장
	 * 
	 * @param cmCdGrp
	 * @param userSeq
	 * @return
	 */
	public CmCdGrp saveCmCdGrp(CmCdGrp cmCdGrp, Long updatorSeq) {
		cmCdGrp.setDelYn(Yn.N);
		cmCdGrp.setCreatorSeq(updatorSeq);
		cmCdGrp.setCreDt(LocalDateTime.now());
		cmCdGrp.setUpdatorSeq(updatorSeq);
		cmCdGrp.setUpdDt(LocalDateTime.now());
		
		return cmCdGrpRepository.save(cmCdGrp);
	}

	/**
	 * 코드 그룹 삭제
	 * 
	 * @param cdGrp
	 * @param userSeq
	 * @return
	 */
	public boolean removeCmCdGrp(String cdGrp, Long updatorSeq) {
		CmCdGrp cmCdGrp = cmCdGrpRepository.getById(cdGrp);
		cmCdGrp.setDelYn(Yn.Y);
		cmCdGrp.setUpdatorSeq(updatorSeq);
		cmCdGrp.setUpdDt(LocalDateTime.now());
		
		cmCdGrpRepository.save(cmCdGrp);
		
		return true;
	}
}
