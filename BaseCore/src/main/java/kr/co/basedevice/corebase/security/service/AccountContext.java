package kr.co.basedevice.corebase.security.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.cm.CmUserPwd;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountContext extends User {

	private static final long serialVersionUID = 8334928817269522057L;

	private CmUser cmUser;

	public AccountContext(
			CmUser cmUser, 
			CmUserPwd cmUserPwd, 
			boolean enabled, 
			boolean accountNonExpired,
			boolean credentialsNonExpired, 
			boolean accountNonLocked,
			List<GrantedAuthority> roles) {

		super(cmUser.getLoginId(), cmUserPwd.getUserPwd(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, roles);
		this.cmUser = cmUser;
	}
}
