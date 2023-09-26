package kr.co.basedevice.corebase.security.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.cm.CmUserPwd;
import kr.co.basedevice.corebase.domain.code.UserStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.repository.cm.CmRoleRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserPwdRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserRepository;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Value("${login.fail-cnt.limit:5}")
	private Integer limitfailCnt;
	
    @Autowired private CmUserRepository cmUserRepository;
    @Autowired private CmUserPwdRepository cmUserPwdRepository;
    @Autowired private CmRoleRepository cmRoleRepository;
    
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        CmUser cmUser = cmUserRepository.findByLoginIdAndDelYn(loginId, Yn.N);
        if (cmUser == null) {        	
        	throw new UsernameNotFoundException("No user found with username: " + loginId);
        }
        
        List<CmUserPwd> cmUserPwdList = cmUserPwdRepository.findByUserSeqAndDelYnOrderByUserPwdSeqDesc(cmUser.getUserSeq(), Yn.N);
        if (cmUserPwdList == null || cmUserPwdList.isEmpty()) {        	
        	throw new UsernameNotFoundException("No user found with username or password :" + loginId);
        }
            
        Set<String> userRoles = cmRoleRepository.findByUserSeq(cmUser.getUserSeq())
                .stream()
                .map(userRole -> ("ROLE_" + userRole.getRoleSeq().toString()))
                .collect(Collectors.toSet());

        List<GrantedAuthority> collect = userRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        
        // 유효성을 체크하고 넘겨 준다면....
        LocalDate now = LocalDate.now();		
		boolean enabled = UserStatCd.ENABLED.equals(cmUser.getUserStatCd());
		boolean accountNonExpired = cmUser.getAcuntExpDt() != null && now.isBefore(cmUser.getAcuntExpDt());
		boolean credentialsNonExpired = cmUserPwdList.get(0).getPwdExpDt() != null && now.isBefore(cmUserPwdList.get(0).getPwdExpDt());
		boolean accountNonLocked = cmUser.getLoginFailCnt() < limitfailCnt;
		
		if(!enabled) {
			throw new DisabledException("Not Enabled"); 
		}
		
		if(!accountNonExpired) {
			throw new DisabledException("Account Expired : " + cmUser.getAcuntExpDt()); 
		}
		
		if(!credentialsNonExpired) {
			throw new CredentialsExpiredException("Password Expired : " + cmUserPwdList.get(0).getPwdExpDt()); 
		}        

		if(!accountNonLocked) {
			throw new LockedException("Account Locked : Login Fail Count " + cmUser.getLoginFailCnt()); 
		}
				
        return new AccountContext(cmUser, cmUserPwdList.get(0), collect);
    }
}