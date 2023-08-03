package kr.co.basedevice.corebase.restController.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.dto.common.UserInfoDto;
import kr.co.basedevice.corebase.search.common.SearchUserInfo;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.common.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/system/user_mgt")
@RequiredArgsConstructor
public class UserMgtRestController {
	
	final private UserService userService;
	
	/**
	 * 사용자 정보 조회
	 * 
	 * @param searchUserInfo
	 * @param page
	 * @return
	 */
	@GetMapping("/user_info_list.json")
	public ResponseEntity<Page<UserInfoDto>> findUserInfoList(SearchUserInfo searchUserInfo, Pageable page){

		if(page == null) {
			page = PageRequest.of(0, 10);
		}
		
		if(searchUserInfo == null) {
			searchUserInfo = new SearchUserInfo();
		}
		
		Page<UserInfoDto> pageUserInfo = userService.pageUserInfo(searchUserInfo, page);
		
		return ResponseEntity.ok(pageUserInfo);
	}
	
	

	public CmUser sssd() {
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		return cmUser;
	}
}
