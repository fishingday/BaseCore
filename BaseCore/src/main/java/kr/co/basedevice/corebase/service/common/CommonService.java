package kr.co.basedevice.corebase.service.common;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.cm.CmCdDtl;
import kr.co.basedevice.corebase.domain.cm.CmCdDtlId;
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

	/**
	 * 코드 상세 조회
	 * 
	 * @param grpCd
	 * @param cd
	 * @return
	 */
	public CmCdDtl findCmCdDtlById(CmCdDtlId cmCdDtlId) {				
		Optional<CmCdDtl> cmCdDtl = cmCdDtlRepository.findById(cmCdDtlId);
		
		if(cmCdDtl.isEmpty()) {
			return null;
		}else {
			return cmCdDtl.get();
		}
	}

	/**
	 * 코드 상세 저장
	 * 
	 * @param cmCdDtl
	 * @param updatorSeq
	 * @return
	 */
	public CmCdDtl saveCmCdDtl(CmCdDtl cmCdDtl, Long updatorSeq) {
		cmCdDtl.setDelYn(Yn.N);
		cmCdDtl.setCreatorSeq(updatorSeq);
		cmCdDtl.setCreDt(LocalDateTime.now());
		cmCdDtl.setUpdatorSeq(updatorSeq);
		cmCdDtl.setUpdDt(LocalDateTime.now());
		
		return cmCdDtlRepository.save(cmCdDtl);
	}

	/**
	 * 코드 삭제
	 * 
	 * @param grpCd
	 * @param cd
	 * @param updatorSeq
	 * @return
	 */
	public boolean removeCmCdDtl(CmCdDtlId cmCdDtlId, Long updatorSeq) {			
		CmCdDtl cmCdDtl = cmCdDtlRepository.getById(cmCdDtlId);	
		
		cmCdDtl.setDelYn(Yn.Y);
		cmCdDtl.setUpdatorSeq(updatorSeq);
		cmCdDtl.setUpdDt(LocalDateTime.now());
		
		cmCdDtlRepository.save(cmCdDtl);
		
		return true;
	}

	/**
	 * 코드 순서 변경
	 * 
	 * @param grpCd
	 * @param chgCd
	 * @param chgOrd
	 * @param tgtCd
	 * @param tgtOrd
	 * @param userSeq
	 * @return
	 */
	public boolean chgOrderCmCdDtl(String grpCd, String chgCd, Integer chgOrd, String tgtCd, Integer tgtOrd, Long updatorSeq) {
		
		CmCdDtlId chgCdDtlId = new CmCdDtlId(grpCd, chgCd);
		CmCdDtl chgCdDtl = cmCdDtlRepository.getById(chgCdDtlId);
		
		chgCdDtl.setPrntOrd(chgOrd);
		chgCdDtl.setDelYn(Yn.Y);
		chgCdDtl.setUpdatorSeq(updatorSeq);
		chgCdDtl.setUpdDt(LocalDateTime.now());
		
		CmCdDtlId tgtCdDtlId = new CmCdDtlId(grpCd, tgtCd);
		CmCdDtl tgtCdDtl = cmCdDtlRepository.getById(tgtCdDtlId);
		
		tgtCdDtl.setPrntOrd(tgtOrd);
		tgtCdDtl.setDelYn(Yn.Y);
		tgtCdDtl.setUpdatorSeq(updatorSeq);
		tgtCdDtl.setUpdDt(LocalDateTime.now());
		
		return true;
	}
}
