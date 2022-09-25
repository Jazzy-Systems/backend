package com.jazzysystems.backend.role;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jazzysystems.backend.exception.NoSuchElementFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        if (role.getRoleName() != null) {
            return roleRepository.save(role);
        } else {
            throw new IllegalStateException("Bad Request");
        }
    }

    @Override
    public Role findRolebyRoleName(String roleName) {
        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(roleName);
        if (roleOptional.isPresent()) {
            return roleOptional.get();
        } else {
            throw new NoSuchElementFoundException("Role Not Found");
        }
    }

}
