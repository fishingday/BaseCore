package kr.co.basedevice.corebase.security.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import kr.co.basedevice.corebase.domain.cm.CmOrg;
import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.cm.CmUserAlowIp;
import kr.co.basedevice.corebase.domain.cm.CmUserPwd;
import kr.co.basedevice.corebase.dto.MyMenuDto;
import kr.co.basedevice.corebase.dto.common.MenuDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountContext extends User {

	private static final long serialVersionUID = 8334928817269522057L;

	private CmUser cmUser;
	
	private CmRole currRole;
	
	private List<CmRole> authRoleList;
	
	private List<CmUserAlowIp> allowIpList;
	
	private List<CmOrg> orgList;
	
	private CmOrg currOrg;
	
	private MyMenuDto myMenu;
	
	private MenuDto currMenu;

	public AccountContext(
			CmUser cmUser, 
			CmUserPwd cmUserPwd, 
			List<GrantedAuthority> roles) {
		super(cmUser.getLoginId(), cmUserPwd.getUserPwd(), roles);
		
		this.cmUser = cmUser;
	}

	public void setCurrMenuSeq(Long menuSeq) {
		currMenu = myMenu.getMenuDto(menuSeq);
	}
}
