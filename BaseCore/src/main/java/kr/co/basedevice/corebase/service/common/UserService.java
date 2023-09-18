package kr.co.basedevice.corebase.service.common;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.cm.CmOrg;
import kr.co.basedevice.corebase.domain.cm.CmOrgUserMap;
import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.cm.CmUserAlowIp;
import kr.co.basedevice.corebase.domain.cm.CmUserPwd;
import kr.co.basedevice.corebase.domain.cm.CmUserRoleMap;
import kr.co.basedevice.corebase.domain.code.UserStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.dto.MyMenuDto;
import kr.co.basedevice.corebase.dto.common.MenuDto;
import kr.co.basedevice.corebase.dto.common.UserInfoDto;
import kr.co.basedevice.corebase.dto.system.SaveUserInfo;
import kr.co.basedevice.corebase.dto.system.SaveUserPwd;
import kr.co.basedevice.corebase.dto.system.SaveUserRole;
import kr.co.basedevice.corebase.dto.user.ChgUserPwdDto;
import kr.co.basedevice.corebase.exception.MenuSettingException;
import kr.co.basedevice.corebase.repository.cm.CmMenuRepository;
import kr.co.basedevice.corebase.repository.cm.CmOrgRepository;
import kr.co.basedevice.corebase.repository.cm.CmOrgUserMapRepository;
import kr.co.basedevice.corebase.repository.cm.CmRoleRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserAlowIpRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserPwdRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserRoleMapRepository;
import kr.co.basedevice.corebase.search.common.SearchUserInfo;
import kr.co.basedevice.corebase.security.service.AccountContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
	
    @Value("${login.set.acunt_exp_dt:365}")
	private Long longAccuntExpDt;
    
    @Value("${login.set.pwd_exp_dt:90}")
	private Long longPwdExpDt;
    
    @Value("${login.set.ban_same_pwd:3}")
    private Long banSamePwd;
    
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    
	final private CmUserRepository cmUserRepository;
	final private CmRoleRepository cmRoleRepository;
	final private CmUserRoleMapRepository cmUserRoleMapRepository;  
	final private CmUserPwdRepository cmUserPwdRepository;
	final private CmMenuRepository cmMenuRepository;
	final private CmOrgRepository cmOrgRepository;
	final private CmUserAlowIpRepository cmUserAlowIpRepository;
	final private CmOrgUserMapRepository cmOrgUserMapRepository;

	
	public CmUser saveCmUser(CmUser cmUser) {
		return cmUserRepository.save(cmUser);
	}

	public List<CmRole> findByUserSeq4CmRole(Long userSeq) {
		return cmRoleRepository.findByUserSeq(userSeq);
	}
	
	/**
	 * 사용자 권한 별 메뉴 목록
	 * - DBMS에 종속적인 뤄리 사용을 배제하고, 단순 쿼리 위주로...
	 * 
	 * @param userSeq
	 * @param roleSeq
	 * @return
	 */	
	public MyMenuDto findRolesMenuWithSetting(Long userSeq, Long roleSeq ) {
		// 일단... 사용자 역할에 할당된 말단 메뉴 목록을 조회하고..
		List<CmMenu> userRoleMenuList = cmMenuRepository.findUserRoleMenu(userSeq, roleSeq);
		
		Map<Long, MenuDto> upMenuMap = new HashMap<>(1);
		
		Set<MenuDto> topMenuSet = new HashSet<>(1); 
		
		for(CmMenu leafMenu : userRoleMenuList) {
			Long upMenuSeq = leafMenu.getUpMenuSeq();
			if(upMenuSeq == null) {
				topMenuSet.add(new MenuDto(leafMenu));
			}else {
				topMenuSet.add(getTopMenu(new MenuDto(leafMenu), upMenuMap));
			}
		}
		
		// 사용자가 가진 메뉴 목록을 포함하는 최상위 메뉴 목록을....
		MyMenuDto myMenuDto = new MyMenuDto(topMenuSet);				
				
		return myMenuDto;
	}

	/**
	 * 최상위 메뉴를 찾는다.
	 * 
	 * @param leafMenu
	 * @param upMenuMap
	 * @return
	 */
	private MenuDto getTopMenu(MenuDto leafMenu, Map<Long, MenuDto> upMenuMap) {		
		MenuDto upMenu = upMenuMap.get(leafMenu.getUpMenuSeq());
		
		if(upMenu == null) {
			CmMenu cmMenu = cmMenuRepository.findById(leafMenu.getUpMenuSeq()).get();
			if(cmMenu != null) {
				upMenu = new MenuDto(cmMenu);
				upMenuMap.put(cmMenu.getMenuSeq(), upMenu);
			}else {
				throw new MenuSettingException(leafMenu.getMenuNm() + "의 상위 메뉴를 찾을 수 없습니다. : UP_MENU_SEQ - " + leafMenu.getUpMenuSeq() ); // 메뉴 설정 오류 인데...
			}
		}
		
		if(upMenu != null) {
			leafMenu.setUpMenu(upMenu); // 상위 메뉴를 세팅하고..
			upMenu.addSubMenu(leafMenu); // 상위 메뉴 아래 ...
		}
		
		if(upMenu.getUpMenuSeq() != null) { // 조상이 있다.
			return getTopMenu(upMenu, upMenuMap);
		}else{ // 조상이 없다면... top!
			return upMenu;
		}
	}
	
	/**
	 * 사용자 목록 조회
	 * 
	 * @param searchUserInfo
	 * @param pageable
	 * @return
	 */
	public Page<UserInfoDto> pageUserInfo(SearchUserInfo searchUserInfo, Pageable pageable){
		Page<UserInfoDto> pageUserInfo = cmUserRepository.pageUserInfo(searchUserInfo, pageable);
		
		if(pageUserInfo != null && !pageUserInfo.isEmpty()) {
			int num = pageUserInfo.getNumber() * pageUserInfo.getSize() + 1;
			for(UserInfoDto userInfoDto : pageUserInfo.getContent()) {
				userInfoDto.setNum(num++);
				userInfoDto.setCmRoleList(cmRoleRepository.findByUserSeq(userInfoDto.getUserSeq()));
				userInfoDto.setCmOrgList(cmOrgRepository.findByUserSeq(userInfoDto.getUserSeq()));
				if(userInfoDto.getCmOrgList() != null && !userInfoDto.getCmOrgList().isEmpty()) {
					userInfoDto.setOrgSeq(userInfoDto.getCmOrgList().get(0).getOrgSeq());
				}
			}
		}
		
		return pageUserInfo;
	}

	/**
	 * 사용자 정보 편집
	 * - 이미 있는 사용자를 대상으로 한다.
	 * 
	 * @param chgUserInfo
	 * @param userSeq
	 */
	public boolean chgUserInfo(SaveUserInfo saveUserInfo) {
		
		CmUser cmUser = cmUserRepository.getById(saveUserInfo.getUserSeq());		
		if(cmUser != null) {
			cmUser.setLoginId(saveUserInfo.getLoginId());
			cmUser.setUserNm(saveUserInfo.getUserNm());
			cmUser.setUserTelNo(saveUserInfo.getUserTelNo());
			cmUser.setLoginFailCnt(saveUserInfo.getLoginFailCnt());
			cmUser.setUserStatCd(saveUserInfo.getUserStatCd());
			cmUser.setAcuntExpDt(saveUserInfo.getAcuntExpDt());
			
			cmUser.setDelYn(Yn.N);
			cmUserRepository.save(cmUser);
			
			// 일단 모든 역할을 제거한다.
			List<CmUserRoleMap> cmUserRoleMapList = cmUserRoleMapRepository.findByUserSeqAndDelYn(saveUserInfo.getUserSeq(), Yn.N);
			if(cmUserRoleMapList != null && !cmUserRoleMapList.isEmpty()) {
				for(CmUserRoleMap cmUserRoleMap : cmUserRoleMapList) {
					cmUserRoleMap.setDelYn(Yn.Y);
				}
				cmUserRoleMapRepository.saveAll(cmUserRoleMapList);
			}
			
			// 역할을 찾아 저장한다.
			if(saveUserInfo.getRoleSeqList() != null && !saveUserInfo.getRoleSeqList().isEmpty()) {
				int prntOrd = 1;
				for(Long roleSeq : saveUserInfo.getRoleSeqList()) {
					CmUserRoleMap cmUserRoleMap = new CmUserRoleMap();
					cmUserRoleMap.setUserSeq(saveUserInfo.getUserSeq());
					cmUserRoleMap.setRoleSeq(roleSeq);
					cmUserRoleMap.setPrntOrd(prntOrd++);
					
					cmUserRoleMap.setDelYn(Yn.N);
					
					cmUserRoleMapRepository.save(cmUserRoleMap);
				}
			}
			
			// 일단 모든 조직을 제거한다.
			List<CmOrgUserMap> cmOrgUserMapList =cmOrgUserMapRepository.findByUserSeqAndDelYn(saveUserInfo.getUserSeq(), Yn.N);
			if(cmOrgUserMapList != null && !cmOrgUserMapList.isEmpty()){
				for(CmOrgUserMap cmOrgUserMap: cmOrgUserMapList) {
					cmOrgUserMap.setDelYn(Yn.Y);
				}
				cmOrgUserMapRepository.saveAll(cmOrgUserMapList);
			}
			
			if(!ObjectUtils.isEmpty(saveUserInfo.getOrgSeq())) {
				CmOrgUserMap cmOrgUserMap = new CmOrgUserMap();
				cmOrgUserMap.setOrgSeq(saveUserInfo.getOrgSeq());
				cmOrgUserMap.setUserSeq(saveUserInfo.getUserSeq());
				cmOrgUserMap.setDelYn(Yn.N);
				cmOrgUserMapRepository.save(cmOrgUserMap);
			}
			
			return true;
		}
		
		return false;
	}

	/**
	 * 일괄 사용자 패스워드 변경
	 * 
	 * @param userSeqList
	 * @param chgPwd
	 * @param userSeq
	 * @return
	 */
	public boolean chgBulkUserPwd(SaveUserPwd saveUserPwd) {

		for(Long userSeq : saveUserPwd.getUserSeqList()){
			List<CmUserPwd> cmUserPwdList = cmUserPwdRepository.findByUserSeqAndDelYnOrderByUserPwdSeqDesc(userSeq, Yn.N);
			if(cmUserPwdList != null && !cmUserPwdList.isEmpty()) {
				for(CmUserPwd cmUserPwd : cmUserPwdList) {
					cmUserPwd.setDelYn(Yn.Y);
				}
				cmUserPwdRepository.saveAll(cmUserPwdList);
			}
			
			CmUserPwd cmUserPwd = new CmUserPwd();
			cmUserPwd.setUserSeq(userSeq);
			cmUserPwd.setUserPwd(passwordEncoder.encode(saveUserPwd.getChgPwd()));
			cmUserPwd.setPwdExpDt(LocalDate.now().plusDays(longAccuntExpDt.longValue()));
			cmUserPwd.setDelYn(Yn.N);
			cmUserPwdRepository.save(cmUserPwd);
			
			// 패스워드를 변경한다는 의미는 사용자의 상태를 로그인할 수 있도록 하다는 의미다.
			// 따라서 사용자의 상태와 계정만료일도 수정해 줘야...
			CmUser cmUser = cmUserRepository.getById(userSeq);
			
			cmUser.setAcuntExpDt(saveUserPwd.getAcuntExpDt());
			cmUser.setUserStatCd(UserStatCd.ENABLED);
			cmUser.setDelYn(Yn.N);
			cmUserRepository.save(cmUser);			
		}
		
		return true;
	}

	/**
	 * 일괄 사용자 역할 변경
	 * 
	 * @param userSeqList
	 * @param roleCdList
	 * @param userSeq
	 * @return
	 */
	public boolean chgBulkUserRole(SaveUserRole saveUserRole) {

		List<CmUserRoleMap> addCmUserRoleMapList = new ArrayList<>();
		for(Long userSeq : saveUserRole.getUserSeqList()){
			// 1. 사용자의 역할을 제거한다.
			List<CmUserRoleMap> cmUserRoleMapList = cmUserRoleMapRepository.findByUserSeqAndDelYn(userSeq, Yn.N);
			if(cmUserRoleMapList != null && !cmUserRoleMapList.isEmpty()) {
				for(CmUserRoleMap cmUserRoleMap : cmUserRoleMapList) {
					cmUserRoleMap.setDelYn(Yn.Y);
				}
				cmUserRoleMapRepository.saveAll(cmUserRoleMapList);
			}
			
			int prntOrd = 1;
			for(Long roleSeq : saveUserRole.getRoleSeqList()) {
				CmUserRoleMap cmUserRoleMap = new CmUserRoleMap();
				cmUserRoleMap.setUserSeq(userSeq);
				cmUserRoleMap.setRoleSeq(roleSeq);
				cmUserRoleMap.setPrntOrd(prntOrd++);
				
				cmUserRoleMap.setDelYn(Yn.N);
				addCmUserRoleMapList.add(cmUserRoleMap);
			}
		}
		
		if(!addCmUserRoleMapList.isEmpty()) {
			cmUserRoleMapRepository.saveAll(addCmUserRoleMapList);
		}
		
		return true;
	}

	/**
	 * 일괄 사용자 삭제
	 * 
	 * @param userSeqList
	 * @param userSeq
	 * @return
	 */
	public boolean removeBulkUser(List<Long> userSeqList) {
		
		for(Long userSeq : userSeqList){
			// 1. 사용자의 역할을 제거한다.
			List<CmUserRoleMap> cmUserRoleMapList = cmUserRoleMapRepository.findByUserSeqAndDelYn(userSeq, Yn.N);
			if(cmUserRoleMapList != null && !cmUserRoleMapList.isEmpty()) {
				for(CmUserRoleMap cmUserRoleMap : cmUserRoleMapList) {
					cmUserRoleMap.setDelYn(Yn.Y);
				}
				cmUserRoleMapRepository.saveAll(cmUserRoleMapList);
			}
			// 9. 마지막으로 사용자 정보를 비활성화 시키고 삭제 처리한다.	
			CmUser cmUser = cmUserRepository.getById(userSeq);			
			cmUser.setUserStatCd(UserStatCd.DISABLED); // 삭제 처리
			cmUser.setDelYn(Yn.Y);
			cmUserRepository.save(cmUser);
		}
		return true;
	}

	/**
	 * 로그인 성공 시 필요한 나머지 정보를 설정한다. 
	 * 
	 * @param account
	 */
	public void setOtherInfo(AccountContext account) {		
		if(account == null || account.getCmUser() == null) {
			return;
		}
		
		CmUser cmUser = account.getCmUser();
        List<CmRole> cmRoleList = cmRoleRepository.findByUserSeq(cmUser.getUserSeq());
        if(cmRoleList != null && !cmRoleList.isEmpty()) {
        	account.setAuthRoleList(cmRoleList);
            account.setCurrRole(cmRoleList.get(0));
                    
            // 현재 권한의 메뉴 목록을 설정한다.
            account.setMyMenu(this.findRolesMenuWithSetting(cmUser.getUserSeq(), account.getCurrRole().getRoleSeq()));
        }

        // 사용자의 조직 정보 조회
        List<CmOrg> cmOrgList = cmOrgRepository.findByUserSeq(cmUser.getUserSeq());
        if(cmOrgList != null && !cmOrgList.isEmpty()) {
        	account.setOrgList(cmOrgList);
        	account.setCurrOrg(cmOrgList.get(0));
        }        
        
        // 사용자의 허용 IP가 설정되어 있다면..
        List<CmUserAlowIp> cmUserAlowIpList = cmUserAlowIpRepository.findByUserSeqAndDelYn(cmUser.getUserSeq(), Yn.N);
        if(cmUserAlowIpList != null && !cmUserAlowIpList.isEmpty()) {
        	account.setAllowIpList(cmUserAlowIpList);
        }
	}

	/**
	 * 사용자 정보 조회
	 * 
	 * @param userSeq
	 * @return
	 */
	public CmUser findByCmUser(Long userSeq) {
		Optional<CmUser> cmUser = cmUserRepository.findById(userSeq);
		return cmUser.get();
	}

	public boolean saveUserInfo(UserInfoDto userInfoDto) {
		CmUser cmUser = cmUserRepository.getById(userInfoDto.getUserSeq());
		
		cmUser.setUserNm(userInfoDto.getUserNm());
		cmUser.setUserTelNo(userInfoDto.getUserTelNo());
		
		cmUserRepository.save(cmUser);
		
		return true;
	}

	/**
	 * LoginId 존재 여부 
	 * 
	 * @param loginId
	 * @return
	 */
	public boolean existsLoginId(String loginId) {
		Long cnt = cmUserRepository.countByLoginIdAndDelYn(loginId, Yn.N);
		
		if(cnt > 0L) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 사용자 패스워드 검증
	 * 
	 * @param userSeq
	 * @param userPwd
	 * @return
	 */
	public boolean verifyUserPwd(Long userSeq, String userPwd) {
		
		List<CmUserPwd> cmUserPwdList = cmUserPwdRepository.findByUserSeqAndDelYn(userSeq, Yn.N);
        
		for(CmUserPwd cmUserPwd : cmUserPwdList) {
			if (!passwordEncoder.matches(userPwd, cmUserPwd.getUserPwd())) {
	        	throw new BadCredentialsException("Invalid password");
	        }
		}
		
		return true;
	}

	/**
	 * 사용자 패스워드 변경
	 * 
	 * @param userSeq
	 * @param chgUserPwd
	 * @return
	 */
	public boolean chgUserPwd(Long userSeq, ChgUserPwdDto chgUserPwd) {
		
		// 동일 패스워드 제한
		List<CmUserPwd> cmUserPwdList = cmUserPwdRepository.findByUserSeqOrderByCreDtDesc(userSeq);
		if(cmUserPwdList != null && !cmUserPwdList.isEmpty()) {
			for(int i = 0; i < cmUserPwdList.size(); i++){
				if(banSamePwd <= i){
					break;
				}
				
				CmUserPwd cmUserPwd = cmUserPwdList.get(i);
				
				if(passwordEncoder.matches(chgUserPwd.getNewPwd(), cmUserPwd.getUserPwd())) {
					throw new BadCredentialsException("사용했던 패스워드를 다시 사용할 수 없습니다.");
				}
			}	
		}		
		
		// 기존 패스워드 삭제 처리
		List<CmUserPwd> cmUserPwds = cmUserPwdRepository.findByUserSeqAndDelYn(userSeq, Yn.N);
		for(CmUserPwd cmUserPwd : cmUserPwds) {
			cmUserPwd.setDelYn(Yn.Y);
			cmUserPwdRepository.save(cmUserPwd);
		}		
		
		// 신규 패스워드 입력
		CmUserPwd newUserPwd = new CmUserPwd();
		newUserPwd.setUserSeq(userSeq);
		newUserPwd.setUserPwd(passwordEncoder.encode(chgUserPwd.getNewPwd()));
		newUserPwd.setPwdExpDt(LocalDate.now().plusDays(longPwdExpDt));
		newUserPwd.setDelYn(Yn.N);		
		
		return true;
	}
	

	/**
	 * 사용자 허용 IP
	 * 
	 * @param userSeq
	 * @return
	 */
	public List<CmUserAlowIp> findByUserSeq4CmUserAlowIp(Long userSeq) {
		List<CmUserAlowIp> cmUserAlowIpList = cmUserAlowIpRepository.findByUserSeqAndDelYn(userSeq, Yn.N);
		
		return cmUserAlowIpList;
	}

	/**
	 * 허용 IP 저장
	 * 
	 * @param cmUserAlowIp
	 * @return
	 */
	public boolean saveUserAllowIp(CmUserAlowIp cmUserAlowIp) {
		// 1. 동일한 IP가 없는지... 확인 
		Long cnt = cmUserAlowIpRepository.countByUserSeqAndAlowIpAndDelYn(cmUserAlowIp.getUserSeq(), cmUserAlowIp.getAlowIp(), Yn.N);
		if(cnt > 0L) {
			cmUserAlowIp.setDelYn(Yn.N);
			cmUserAlowIpRepository.save(cmUserAlowIp);
			
			return true;
		} 
		
		return false;
	}	

	/**
	 * 허용 IP 제외
	 * 
	 * @param userAlowIpSeq
	 * @return
	 */
	public boolean removeUserAllowIp(Long userAlowIpSeq) {
		
		CmUserAlowIp cmUserAlowIp = cmUserAlowIpRepository.getById(userAlowIpSeq);
		cmUserAlowIp.setDelYn(Yn.Y);		
		cmUserAlowIpRepository.save(cmUserAlowIp);
		
		return true;
	}
}
