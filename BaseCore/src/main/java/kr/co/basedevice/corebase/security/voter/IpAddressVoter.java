package kr.co.basedevice.corebase.security.voter;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.security.service.AccountContext;

public class IpAddressVoter implements AccessDecisionVoter<Object> {

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		
		WebAuthenticationDetails details  = (WebAuthenticationDetails) authentication.getDetails();
		String remoteAddress = details.getRemoteAddress();

		// TODO : 사용자 정보의 조회 여부를 보고... 결정하자.
		CmUser cmUser = ((AccountContext) authentication.getPrincipal()).getCmUser();
		
		int result = ACCESS_DENIED;
		
		if(cmUser != null) {
			return ACCESS_ABSTAIN;
		}
		
		if(ACCESS_DENIED == result) {
			throw new AccessDeniedException("Invailid IpAddress");
		}
		
		
		
		return 0;
	}

}
