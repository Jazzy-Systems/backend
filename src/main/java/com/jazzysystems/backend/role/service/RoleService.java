package com.jazzysystems.backend.role.service;

import java.util.List;

import com.jazzysystems.backend.role.Role;
import com.jazzysystems.backend.role.dto.RoleDTO;

public interface RoleService {

    Role saveRole(RoleDTO roledDto);

    Role findbyRoleName(String roleName);

    Role updateRole(Long roleId, RoleDTO roleDTO);

    void deleteRole(RoleDTO roleDTO);

    void deleteRoleById(Long roleId);

    Role findRoleById(Long roleId);

    List<Role> findAll();

    Boolean existsById(Long roleId);
}
