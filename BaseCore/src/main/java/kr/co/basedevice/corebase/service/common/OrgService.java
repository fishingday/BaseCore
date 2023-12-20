package kr.co.basedevice.corebase.service.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.basedevice.corebase.domain.cm.CmOrg;
import kr.co.basedevice.corebase.domain.cm.CmOrgUserMap;
import kr.co.basedevice.corebase.domain.cm.CmOrgUserMapId;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.dto.system.ChooseUsersOrg;
import kr.co.basedevice.corebase.dto.system.OrgInfoDto;
import kr.co.basedevice.corebase.exception.OperationException;
import kr.co.basedevice.corebase.repository.cm.CmOrgRepository;
import kr.co.basedevice.corebase.repository.cm.CmOrgUserMapRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class OrgService {

	final private CmOrgRepository cmOrgRepository;
	final private CmOrgUserMapRepository cmOrgUserMapRepository;
	final private CmUserRepository cmUserRepository;
	
	/**
	 * 조직 목록 (전체)
	 * 
	 * @return
	 */
	@Cacheable(value = "ORG", key="'ALL'")
	public List<OrgInfoDto> findAllOrg() {
		List<CmOrg> cmOrgList = cmOrgRepository.findByDelYn(Yn.N);
		
		Map <Long, OrgInfoDto> orgMap = new HashMap<>(cmOrgList.size());
		for(CmOrg cmOrg: cmOrgList) {
			orgMap.put(cmOrg.getOrgSeq(), new OrgInfoDto(cmOrg));
		}
		
		// 부모와 자식의 인연을 이어주고..
		for(OrgInfoDto orgInfoDto: orgMap.values()) {
			if(!ObjectUtils.isEmpty(orgInfoDto.getUpOrgSeq())) {
				OrgInfoDto parentOrg = orgMap.get(orgInfoDto.getUpOrgSeq());
				orgInfoDto.setParentOrgInfo(parentOrg);
				parentOrg.getSubOrgInfoList().add(orgInfoDto);
			}
		}
						
		List<OrgInfoDto> orgInfoDtoList = new ArrayList<>(orgMap.values());
		Collections.sort(orgInfoDtoList);
		
		return orgInfoDtoList;
	}
	
	/**
	 * 부모 조직만 조회
	 * 
	 * @return
	 */
	@Cacheable(value = "ORG", key="'Parent'")
	public List<OrgInfoDto> findByParentOrg(){
		List<CmOrg> cmOrgList = cmOrgRepository.findByParentOrg();
		List<CmOrg> parentOrgList = cmOrgRepository.findByUpOrgSeqIsNullAndDelYn(Yn.N);
		
		Map <Long, OrgInfoDto> orgMap = new HashMap<>(cmOrgList.size() + parentOrgList.size());
		for(CmOrg cmOrg: cmOrgList) {
			orgMap.put(cmOrg.getOrgSeq(), new OrgInfoDto(cmOrg));
		}
		for(CmOrg cmOrg: parentOrgList) {
			orgMap.put(cmOrg.getOrgSeq(), new OrgInfoDto(cmOrg));
		}
		
		// 부모와 자식의 인연을 이어주고..
		for(OrgInfoDto orgInfoDto: orgMap.values()) {
			if(!ObjectUtils.isEmpty(orgInfoDto.getUpOrgSeq())) {
				OrgInfoDto parentOrg = orgMap.get(orgInfoDto.getUpOrgSeq());
				orgInfoDto.setParentOrgInfo(parentOrg);
			}
		}
		
		List<OrgInfoDto> orgInfoDtoList = new ArrayList<>(orgMap.values());
		Collections.sort(orgInfoDtoList);
		
		return orgInfoDtoList;
	}
	
	/**
	 * 조직 정보 조회 [하위포함]
	 * 
	 * @param orgSeq
	 * @return
	 */
	@Cacheable(value = "ORG", key="#orgSeq")
	public OrgInfoDto findOrg(Long orgSeq) {
		// 먼저 대상 조직을 조회하고...
		OrgInfoDto orgInfoDto = new OrgInfoDto(cmOrgRepository.getById(orgSeq));
		
		if(orgInfoDto != null) {
			// 대상 조직에 상위 조직이 있는지 확인하고..
			if(!ObjectUtils.isEmpty(orgInfoDto.getUpOrgSeq())) {
				OrgInfoDto parentOrg = new OrgInfoDto(cmOrgRepository.getById(orgInfoDto.getUpOrgSeq()));
				orgInfoDto.setParentOrgInfo(parentOrg);
			}
			
			// 하위 조직이 있는지 확인하여...
			List<CmOrg> subOrgList = cmOrgRepository.findByUpOrgSeqAndDelYn(orgSeq, Yn.N);
			if(subOrgList != null && !subOrgList.isEmpty()) {
				for(CmOrg cmOrg : subOrgList) {
					orgInfoDto.getSubOrgInfoList().add(new OrgInfoDto(cmOrg));
				}				
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
	@CacheEvict(value = "ORG", allEntries=true)
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
	@CacheEvict(value = "ORG", allEntries=true)
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

	public List<CmUser> findByCmUser(Long orgSeq) {
		List<CmUser> cmUserList = cmUserRepository.findByOrgSeq(orgSeq);
		
		return cmUserList;
	}

	public List<CmUser> findByExcludeCmUser(Long orgSeq) {
		List<CmUser> cmUserList = cmUserRepository.findByExcludeOrgSeq(orgSeq);
		
		return cmUserList;
	}

	public int addUsers(ChooseUsersOrg chooseUsers) {
		if(chooseUsers.getUserSeqList() != null && !chooseUsers.getUserSeqList().isEmpty()) {
			int inc = 0;
			for(Long userSeq : chooseUsers.getUserSeqList()) {
				CmOrgUserMapId cmOrgUserMapId = new CmOrgUserMapId();
				cmOrgUserMapId.setUserSeq(userSeq);
				cmOrgUserMapId.setOrgSeq(chooseUsers.getOrgSeq());
				Optional<CmOrgUserMap> otpCmOrgUserMap = cmOrgUserMapRepository.findById(cmOrgUserMapId);
				CmOrgUserMap cmOrgUserMap = null;
				
				if(!otpCmOrgUserMap.isPresent()) {
					cmOrgUserMap = new CmOrgUserMap();
					cmOrgUserMap.setUserSeq(userSeq);
					cmOrgUserMap.setOrgSeq(chooseUsers.getOrgSeq());
				}else {
					cmOrgUserMap = otpCmOrgUserMap.get();
				}
				cmOrgUserMap.setDelYn(Yn.N);
				
				cmOrgUserMapRepository.save(cmOrgUserMap);				
				inc++;
			}
			return inc;
		}
		return 0;
	}

	public int removeUsers(ChooseUsersOrg chooseUsers) {
		if(chooseUsers.getUserSeqList() != null && !chooseUsers.getUserSeqList().isEmpty()) {
			int inc = 0;
			for(Long userSeq : chooseUsers.getUserSeqList()) {
				CmOrgUserMapId cmOrgUserMapId = new CmOrgUserMapId();
				cmOrgUserMapId.setUserSeq(userSeq);
				cmOrgUserMapId.setOrgSeq(chooseUsers.getOrgSeq());
				Optional<CmOrgUserMap> otpCmOrgUsereMap = cmOrgUserMapRepository.findById(cmOrgUserMapId);
						
				if(!otpCmOrgUsereMap.isPresent()) {
					continue;
				}
				CmOrgUserMap cmOrgUserMap = otpCmOrgUsereMap.get();
				cmOrgUserMap.setDelYn(Yn.Y);
				
				cmOrgUserMapRepository.save(cmOrgUserMap);				
				inc++;
			}
			return inc;
		}
		return 0;	
	}
}
