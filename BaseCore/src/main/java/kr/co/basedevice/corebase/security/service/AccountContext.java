package kr.co.basedevice.corebase.security.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import kr.co.basedevice.corebase.domain.cm.Account;
import lombok.Data;

@Data
public class AccountContext extends User {
	
  private static final long serialVersionUID = -588659020397086914L;
  
  private Account account;

  public AccountContext(Account account, List<GrantedAuthority> roles) {
    super(account.getUsername(), account.getPassword(), roles);
    this.account = account;
  }
}
