package kr.co.basedevice.corebase.service.common;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.basedevice.corebase.domain.cm.CmCdDtl;
import kr.co.basedevice.corebase.domain.cm.CmCdDtlId;
import kr.co.basedevice.corebase.domain.cm.CmCdGrp;
import kr.co.basedevice.corebase.domain.cm.CmOrg;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.dto.system.OrgInfoDto;
import kr.co.basedevice.corebase.exception.OperationException;
import kr.co.basedevice.corebase.repository.cm.CmCdDtlRepository;
import kr.co.basedevice.corebase.repository.cm.CmCdGrpRepository;
import kr.co.basedevice.corebase.repository.cm.CmOrgRepository;
import kr.co.basedevice.corebase.repository.cm.CmOrgUserMapRepository;
import kr.co.basedevice.corebase.search.common.SearchGrpCd;
import kr.co.basedevice.corebase.search.system.SearchDtlCd;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CommonService {

	private final CmCdGrpRepository cmCdGrpRepository;
	private final CmCdDtlRepository cmCdDtlRepository;
	private final CmOrgRepository cmOrgRepository;
	private final CmOrgUserMapRepository cmOrgUserMapRepository;
	
	/**
	 * 코드 상세 목록 조회
	 * - 그룹코드 조회
	 * 
	 * @param grpCd
	 * @return
	 */
	@Cacheable(value = "CODE", key="#grpCd")
	public List<CmCdDtl> findCmCdDtlByGrpCd(String grpCd){
		List<CmCdDtl> cmCdDtlList = cmCdDtlRepository.findByGrpCd(grpCd);
		
		return cmCdDtlList;
	}
	
	/**
	 * 코드 상세 목록 조회
	 * 
	 * @param searchDtlCd
	 * @return
	 */
	public List<CmCdDtl> getCmCdDtlList(SearchDtlCd searchDtlCd){
		List<CmCdDtl> cmCdDtlList = cmCdDtlRepository.findBySearch(searchDtlCd);
		
		return cmCdDtlList;
	}
	
	/**
	 * 코드 그룹 목록
	 * 
	 * @param searchCodeGrp
	 * @return
	 */
	public List<CmCdGrp> findBySearch(SearchGrpCd searchCodeGrp) {
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
	public CmCdGrp saveCmCdGrp(CmCdGrp cmCdGrp) {
		cmCdGrp.setDelYn(Yn.N);		
		return cmCdGrpRepository.save(cmCdGrp);
	}

	/**
	 * 코드 그룹 삭제
	 * 
	 * @param grpCd
	 * @param userSeq
	 * @return
	 */
	@CacheEvict(value = "CODE", key = "#grpCd")
	public boolean removeCmCdGrp(String grpCd) {
		List<CmCdDtl> cmCdDtlList = cmCdDtlRepository.findByGrpCd(grpCd);
		if(cmCdDtlList != null && !cmCdDtlList.isEmpty()) {
			for(CmCdDtl cmCdDtl : cmCdDtlList) {
				cmCdDtl.setDelYn(Yn.Y);
			}
			cmCdDtlRepository.saveAll(cmCdDtlList);
		}
		
		CmCdGrp cmCdGrp = cmCdGrpRepository.getById(grpCd);
		cmCdGrp.setDelYn(Yn.Y);
		
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
	@Cacheable(value = "CODE", key="#cmCdDtlId.grpCd + '-' + #cmCdDtlId.cd")
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
	@CacheEvict(value = "CODE", key = "#cmCdDtl.grpCd")
	@CachePut(value="CODE", key="#cmCdDtl.grpCd  + '-' +  #cmCdDtl.cd")
	public CmCdDtl saveCmCdDtl(CmCdDtl cmCdDtl) {
		cmCdDtl.setDelYn(Yn.N);
		
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
	@Caching(evict = {
		@CacheEvict(value = "CODE", key = "#cmCdDtlId.grpCd"),
		@CacheEvict(value = "CODE", key = "#cmCdDtlId.grpCd  + '-' +  #cmCdDtlId.cd")
	})
	public boolean removeCmCdDtl(CmCdDtlId cmCdDtlId) {			
		CmCdDtl cmCdDtl = cmCdDtlRepository.getById(cmCdDtlId);	
		
		cmCdDtl.setDelYn(Yn.Y);		
		cmCdDtlRepository.save(cmCdDtl);
		
		return true;
	}
	
	/**
	 * 조직 목록 (전체)
	 * 
	 * @return
	 */
	@Cacheable(value = "ORG", key="'ALL'")
	public List<OrgInfoDto> findAllOrg() {
		
		return null;
	}
	
	/**
	 * 조직 정보 [하위포함]
	 * 
	 * @param orgSeq
	 * @return
	 */
	@Cacheable(value = "ORG", key="#orgSeq")
	public OrgInfoDto findOrg(Long orgSeq) {
		OrgInfoDto orgInfoDto = new OrgInfoDto(cmOrgRepository.getById(orgSeq));
		
		if(!ObjectUtils.isEmpty(orgInfoDto.getUpOrgSeq())) {
			OrgInfoDto parentOrg = new OrgInfoDto(cmOrgRepository.getById(orgInfoDto.getUpOrgSeq()));
			orgInfoDto.setParentOrgInfo(parentOrg);
			for(;true;) {
				if(ObjectUtils.isEmpty(parentOrg.getUpOrgSeq())) {
					break;
				}
				OrgInfoDto upOrg = new OrgInfoDto(cmOrgRepository.getById(orgInfoDto.getUpOrgSeq()));
				parentOrg.setParentOrgInfo(upOrg);
				parentOrg = upOrg;
			}
		}
		
		return orgInfoDto;
	}
	
	/** 
	 * 조직 등록
	 * 
	 * @param cmOrg
	 * @return
	 */
	@CacheEvict(value = "ORG")
	public CmOrg saveCmOrg(CmOrg cmOrg) {
		
		cmOrg.setDelYn(Yn.N);
		
		return cmOrgRepository.save(cmOrg);
	}
	
	/**
	 * 조직 삭제
	 * 
	 * @param orgSeq
	 * @return
	 */
	@CacheEvict(value = "ORG")
	public boolean removeCmOrg(Long orgSeq) {
		
		// 사용자 없어야 조직을 삭제할 수 있음.
		long cnt = cmOrgUserMapRepository.countByOrgSeqAndDelYn(orgSeq, Yn.N);
		if(cnt > 0L) {
			throw new OperationException("소속된 사용자가 있는 조직은 삭제할 수 없습니다.");
		}
		
		CmOrg cmOrg = cmOrgRepository.getById(orgSeq);
		cmOrg.setDelYn(Yn.Y);
		
		cmOrgRepository.save(cmOrg);
		
		return true;
	}
}
