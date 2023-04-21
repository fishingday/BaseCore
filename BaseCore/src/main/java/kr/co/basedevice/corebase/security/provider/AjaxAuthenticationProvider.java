package kr.co.basedevice.corebase.security.provider;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.security.token.AjaxAuthenticationToken;

public class AjaxAuthenticationProvider implements AuthenticationProvider {

	@Value("${login.use2factor}")
	private boolean use2factor;
	
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

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
        
        if(use2factor) {
	        // TODO : OTP 적용
        }

        return new AjaxAuthenticationToken(accountContext.getCmUser(), null, accountContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AjaxAuthenticationToken.class);
    }
}
