package com.jazzysystems.backend.role.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jazzysystems.backend.role.Role;
import com.jazzysystems.backend.role.RoleMapper;
import com.jazzysystems.backend.role.dto.RoleDTO;
import com.jazzysystems.backend.role.repository.RoleRepository;
import com.jazzysystems.backend.exception.NoSuchElementFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Override
    public Role saveRole(RoleDTO roleDTO) {
        Role role = roleMapper.convertDTOtoRole(roleDTO);
        return roleRepository.save(role);
    }

    @Override
    public Role findbyRoleName(String roleName) {
        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(roleName);
        if (roleOptional.isPresent()) {
            return roleOptional.get();
        } else {
            throw new NoSuchElementFoundException("Not Found");
        }
    }

    @Override
    public Role updateRole(Long roleId, RoleDTO roleDTO) {
        Role role = this.findRoleById(roleId);
        role.setRoleName(roleDTO.getRoleName());
        role.setEnabled(roleDTO.isEnabled());
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(RoleDTO roleDTO) {
        Role role = roleMapper.convertDTOtoRole(roleDTO);
        roleRepository.delete(role);
    }

    @Override
    public void deleteRoleById(Long roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    public Role findRoleById(Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new NoSuchElementFoundException("Not Found"));
        return role;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Boolean existsById(Long roleId) {
        return roleRepository.existsById(roleId);
    }

}
