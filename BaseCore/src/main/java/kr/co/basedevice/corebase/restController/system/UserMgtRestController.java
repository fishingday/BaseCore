package kr.co.basedevice.corebase.restController.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.dto.common.UserInfoDto;
import kr.co.basedevice.corebase.dto.system.DeleteUsers;
import kr.co.basedevice.corebase.dto.system.SaveUserInfo;
import kr.co.basedevice.corebase.dto.system.SaveUserPwd;
import kr.co.basedevice.corebase.dto.system.SaveUserRole;
import kr.co.basedevice.corebase.exception.OperationException;
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
	public ResponseEntity<Page<UserInfoDto>> findbyUserInfoList(SearchUserInfo searchUserInfo, Pageable page){

		if(page == null) {
			page = PageRequest.of(0, 10);
		}
		
		if(searchUserInfo == null) {
			searchUserInfo = new SearchUserInfo();
		}
		
		Page<UserInfoDto> pageUserInfo = userService.pageUserInfo(searchUserInfo, page);
		
		return ResponseEntity.ok(pageUserInfo);
	}
	
	/**
	 * 사용자 정보 변경
	 * 
	 * @param userInfo
	 * @return
	 */
	@PutMapping("/chg_user_info.json")
	public ResponseEntity<Boolean> chgUserInfo(SaveUserInfo userInfo) {
		
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		boolean isSave = userService.chgUserInfo(userInfo, cmUser.getUserSeq());
		
		return ResponseEntity.ok(isSave);
	}
		
	/**
	 * 사용자 패스워드 일괄 변경
	 * 
	 * @param userSeqList
	 * @param chgPwd
	 * @return
	 */
	@PutMapping("/bulk_chg_user_pwd.json")
	public ResponseEntity<Boolean> bulkChgUserPwd(SaveUserPwd saveUserPwd) {
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();		
		chkChgSelf(saveUserPwd.getUserSeqList()); // 일괄 변경에 자신의 정보가 포함되지 않도록 한다.
		
		boolean isChg = userService.chgBulkUserPwd(saveUserPwd, cmUser.getUserSeq());
		
		return ResponseEntity.ok(isChg);
	}
	
	/**
	 * 사용자 역할 일괄 변경
	 * 
	 * @param userSeqList
	 * @param roleCdList
	 * @return
	 */
	@PutMapping("/chg_bulk_user_role.json")
	public ResponseEntity<Boolean> bulkChgkUserRole(SaveUserRole saveUserRole) {
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();		
		chkChgSelf(saveUserRole.getUserSeqList()); // 일괄 변경에 자신의 정보가 포함되지 않도록 한다.
		
		boolean isChg = userService.chgBulkUserRole(saveUserRole, cmUser.getUserSeq());
		
		return ResponseEntity.ok(isChg);
	}
	
	/**
	 * 사용자 일괄 삭제
	 * 
	 * @param userSeqList
	 * @return
	 */
	@DeleteMapping("/remove_bulk_user.json")
	public ResponseEntity<Boolean> removeBulkUser(DeleteUsers deleteUsers) {
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();		
		chkChgSelf(deleteUsers.getUserSeqList()); // 일괄 변경에 자신의 정보가 포함되지 않도록 한다.
		
		boolean isRemove = userService.removeBulkUser(deleteUsers.getUserSeqList(), cmUser.getUserSeq());
		
		return ResponseEntity.ok(isRemove);
	}
	
	/**
	 * 자신의 정보가 포함되어 있으면 예외를 발생
	 * 
	 * @param userSeqList
	 */
	private void chkChgSelf(List<Long> userSeqList) {
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		for(Long userSeq : userSeqList) {
			if(cmUser.getUserSeq().longValue() == userSeq.longValue()) {
				throw new OperationException("본인 정보는 해당 화면에서 변경할 수 없습니다."); 
			}
		}
	}
}
