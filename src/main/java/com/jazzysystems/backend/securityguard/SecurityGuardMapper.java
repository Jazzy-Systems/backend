package com.jazzysystems.backend.securityguard;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jazzysystems.backend.securityguard.dto.SecurityGuardDTO;

@Component
public class SecurityGuardMapper {
    public SecurityGuardDTO convertSecurityGuardToDTO(SecurityGuard securityGuard) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(securityGuard, SecurityGuardDTO.class);
    }

    public SecurityGuard convertDTOtoSecurityGuard(SecurityGuardDTO securityGuardDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(securityGuardDTO, SecurityGuard.class);
    }
}
