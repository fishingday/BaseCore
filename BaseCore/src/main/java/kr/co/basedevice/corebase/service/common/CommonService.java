package kr.co.basedevice.corebase.service.common;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.cm.CmCdDtl;
import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.repository.cm.CmCdDtlRepository;
import kr.co.basedevice.corebase.repository.cm.CmRoleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CommonService {

	private final CmRoleRepository cmRoleRepository;	
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
}
