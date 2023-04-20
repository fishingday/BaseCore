package kr.co.basedevice.corebase.security.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.cm.Account;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.repository.cm.CmRoleRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserRepository;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private CmUserRepository cmUserRepository;
    @Autowired private CmRoleRepository cmRoleRepository;
    
    private int limitCnt = Integer.valueOf(5); 

    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        CmUser cmUser = cmUserRepository.findByLoginIdAndDelYn(loginId, "N");
        if (cmUser == null) {        	
        	throw new UsernameNotFoundException("No user found with username: " + loginId);
        }else {
        	int nowDate = Integer.valueOf(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now()));
        	
        	if(!"ENABLE".equals(cmUser.getUserStatCd())) {
        		throw new UsernameNotFoundException("계정 상태에 문제가 있습니다. 관리자에게 문의하세요.");
        	}else if(Integer.parseInt(cmUser.getAcuntExpDt()) < nowDate) {
        		throw new UsernameNotFoundException("계정이 만료 되었습니다.");
        	}else if(limitCnt < cmUser.getLoginFailCnt()) {
        		throw new UsernameNotFoundException("접속 실패 한계를 초과했습니다. 관리자에게 문의하세요. ");
        	}
        }
        
        /**    
        Set<String> userRoles = account.getUserRoles()
                .stream()
                .map(userRole -> userRole.getRoleName())
                .collect(Collectors.toSet());

        List<GrantedAuthority> collect = userRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new AccountContext(account, collect);
        
        */
        return null;
    }
}