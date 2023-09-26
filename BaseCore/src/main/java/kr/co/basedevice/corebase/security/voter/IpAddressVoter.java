package kr.co.basedevice.corebase.security.voter;

import java.util.Collection;
import java.util.List;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import kr.co.basedevice.corebase.domain.cm.CmUserAlowIp;
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
		if(!"anonymousUser".equals(authentication.getPrincipal())) { // 익명 사용자라면 무시하자.
			List<CmUserAlowIp> cmUserAlowIpList = ((AccountContext) authentication.getPrincipal()).getAllowIpList();
			if(cmUserAlowIpList != null && !cmUserAlowIpList.isEmpty()) {
				String remoteAddress = details.getRemoteAddress();				
				for(CmUserAlowIp cmUserAlowIp : cmUserAlowIpList) {
					IpAddressMatcher matcher = new IpAddressMatcher(cmUserAlowIp.getAlowIp().trim());
					if(matcher.matches(remoteAddress)) {
						return ACCESS_ABSTAIN;					
					}
				}
				// 중간에 못나오면 아웃
				throw new AccessDeniedException("Not an allowed IP address");
			}
		}
		
		return ACCESS_ABSTAIN;
	}
}
