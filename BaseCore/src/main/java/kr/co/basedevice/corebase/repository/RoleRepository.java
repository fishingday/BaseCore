package kr.co.basedevice.corebase.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.cm.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

//    Role findByRoleName(String name);
//
//    @Override
//    void delete(Role role);

}
