package kr.co.basedevice.corebase.service;

import java.util.List;

import kr.co.basedevice.corebase.domain.cm.Role;

public interface RoleService {

    Role getRole(long id);

    List<Role> getRoles();

    void createRole(Role role);

    void deleteRole(long id);
}