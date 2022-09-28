package com.jazzysystems.backend.role;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jazzysystems.backend.role.dto.RoleDTO;

@Component

public class RoleMapper {
    
    public RoleDTO convertRoleToDTO(Role role) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(role, RoleDTO.class);
    }

    public Role convertDTOtoRole(RoleDTO roleDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(roleDTO, Role.class);
    }
}
