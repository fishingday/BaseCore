package kr.co.basedevice.corebase.service;

import kr.co.basedevice.corebase.domain.cm.Account;
import kr.co.basedevice.corebase.dto.AccountDto;

import java.util.List;

public interface UserService {

    void createUser(Account account);

    void modifyUser(AccountDto accountDto);

    List<Account> getUsers();

    AccountDto getUser(Long id);

    void deleteUser(Long idx);
}
