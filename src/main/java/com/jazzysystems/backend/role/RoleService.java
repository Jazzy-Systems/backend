package com.jazzysystems.backend.role;

public interface RoleService {

    Role saveRole(Role role);

    Role findRolebyRoleName(String roleName);
}
