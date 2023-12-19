package kr.co.basedevice.corebase.security.provider;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import kr.co.basedevice.corebase.domain.cm.CmUserAlowIp;
import kr.co.basedevice.corebase.security.common.AjaxWebAuthenticationDetails;
import kr.co.basedevice.corebase.security.common.FormWebAuthenticationDetails;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.security.token.AjaxAuthenticationToken;
import kr.co.basedevice.corebase.service.common.UserService;

public class AjaxAuthenticationProvider implements AuthenticationProvider {

	@Value("${login.use2factor:false}")
	private boolean use2factor;
	
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AjaxAuthenticationProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String loginId = authentication.getName();
        String password = (String) authentication.getCredentials();

        AccountContext accountContext = (AccountContext)userDetailsService.loadUserByUsername(loginId);

        if (!passwordEncoder.matches(password, accountContext.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        
        if(use2factor) { // 추가 인증 구현 시 적용
	        String otpKey = ((AjaxWebAuthenticationDetails) authentication.getDetails()).getSecretKey();
	        if (otpKey == null || !otpKey.equals("xxxxx")) {
	            throw new IllegalArgumentException("Invalid Secret");
	        }
        }
        
        List<CmUserAlowIp> cmUserAlowIpList = userService.findByUserSeq4CmUserAlowIp(accountContext.getCmUser().getUserSeq());
        if(cmUserAlowIpList != null && !cmUserAlowIpList.isEmpty()) {
        	boolean isAllowIp = false;
        	String remoteAddress = ((FormWebAuthenticationDetails) authentication.getDetails()).getIpAddr();	
			for(CmUserAlowIp cmUserAlowIp : cmUserAlowIpList) {
				IpAddressMatcher matcher = new IpAddressMatcher(cmUserAlowIp.getAlowIp().trim());
				if(matcher.matches(remoteAddress)) {
					isAllowIp = true;
					break;
				}
			}
			if(!isAllowIp) {
				throw new AccessDeniedException("Invailid Allow IpAddress");
			}
			accountContext.setAllowIpList(cmUserAlowIpList);
        }        
        userService.setOtherInfo(accountContext);

        return new AjaxAuthenticationToken(accountContext, null, accountContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AjaxAuthenticationToken.class);
    }
}
