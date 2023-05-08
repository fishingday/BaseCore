package kr.co.basedevice.corebase.service.common;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.dto.MyMentDto;
import kr.co.basedevice.corebase.repository.cm.CmRoleRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	final private CmUserRepository cmUserRepository;
	final private CmRoleRepository cmRoleRepository;

	
	public void saveCmUser(CmUser cmUser) {
		cmUserRepository.save(cmUser);
	}

	public List<CmRole> findByUserSeq4CmRole(Long userSeq) {
		return cmRoleRepository.findByUserSeq(userSeq);
	}

	// 사용자의 메뉴를 어떻게 설정하지?
	public MyMentDto findOneMyMenu(Long userSeq) {
		return findOneMyMenuWithSetting(userSeq, false);
	}
	
	public MyMentDto findOneMyMenuWithSetting(Long userSeq, boolean isSetMenu) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
