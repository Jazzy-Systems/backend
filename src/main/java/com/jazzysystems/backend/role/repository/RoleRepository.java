package com.jazzysystems.backend.role.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jazzysystems.backend.role.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByRoleName(String roleName);

}
