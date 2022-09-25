package com.jazzysystems.backend.role.service;

import com.jazzysystems.backend.role.Role;

public interface RoleService {

    Role saveRole(Role role);

    Role findRolebyRoleName(String roleName);
}
