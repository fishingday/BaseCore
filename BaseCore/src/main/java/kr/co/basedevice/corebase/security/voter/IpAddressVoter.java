package kr.co.basedevice.corebase.security.voter;

import java.util.Collection;
import java.util.List;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

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
		
		List<CmUserAlowIp> cmUserAlowIpList = ((AccountContext) authentication.getPrincipal()).getAllowIpList();
		
		if(cmUserAlowIpList != null && !cmUserAlowIpList.isEmpty()) {
			String remoteAddress = details.getRemoteAddress();			
			for(CmUserAlowIp cmUserAlowIp : cmUserAlowIpList) {
				if(remoteAddress.equals(cmUserAlowIp.getAlowIp())) {
					return ACCESS_ABSTAIN;					
				}
			}
			// 중간에 못나오면 아웃
			throw new AccessDeniedException("Invailid IpAddress");
		}	
		return ACCESS_ABSTAIN;
	}

}
