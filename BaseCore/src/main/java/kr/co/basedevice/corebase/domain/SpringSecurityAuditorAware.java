package kr.co.basedevice.corebase.domain;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.security.service.AccountContext;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	
        if(authentication == null) {
            return null;
        }
        
        CmUser cmUser = ((AccountContext) authentication.getPrincipal()).getCmUser();
        if(cmUser == null) {
        	return null;
        }

        return Optional.ofNullable(cmUser.getUserSeq());
    }
}
