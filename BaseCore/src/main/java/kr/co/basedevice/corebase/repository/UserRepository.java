package kr.co.basedevice.corebase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.cm.Account;

public interface UserRepository extends JpaRepository<Account, Long> {
//  Account findByUsername(String username);
//  int countByUsername(String username);
}