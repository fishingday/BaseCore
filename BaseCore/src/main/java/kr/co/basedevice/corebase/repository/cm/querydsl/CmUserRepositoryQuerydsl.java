package kr.co.basedevice.corebase.repository.cm.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.basedevice.corebase.dto.common.UserInfoDto;
import kr.co.basedevice.corebase.search.common.SearchUserInfo;

public interface CmUserRepositoryQuerydsl {
	
	/**
	 * 사용자 목록 조회
	 * 
	 * @param searchUserInfo
	 * @param page
	 * @return
	 */
	Page<UserInfoDto> pageUserInfo(SearchUserInfo searchUserInfo, Pageable pageable);
}
