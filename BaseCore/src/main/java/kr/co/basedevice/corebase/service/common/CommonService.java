package kr.co.basedevice.corebase.service.common;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.repository.cm.CmRoleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CommonService {

	private final CmRoleRepository cmRoleRepository;
	
	/**
	 * 역할 목록 조회
	 * 
	 * @return
	 */
	public List<CmRole> getCmRole(){
		return cmRoleRepository.findByDelYn(Yn.N);
	}
}
