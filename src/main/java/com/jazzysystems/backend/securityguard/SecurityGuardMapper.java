package com.jazzysystems.backend.securityguard;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jazzysystems.backend.company.CompanyMapper;
import com.jazzysystems.backend.securityguard.dto.SecurityGuardDTO;

@Component
public class SecurityGuardMapper {
    @Autowired    
    private CompanyMapper companyMapper;

    public SecurityGuardDTO convertSecurityGuardToDTO(SecurityGuard securityGuard) {
        ModelMapper modelMapper = new ModelMapper();
        SecurityGuardDTO securityGuardDTO = modelMapper.map(securityGuard, SecurityGuardDTO.class);
        securityGuardDTO.setCompanyDTO(companyMapper.convertCompanyToDTO(securityGuard.getCompany()));
        return securityGuardDTO;
    }

    public SecurityGuard convertDTOtoSecurityGuard(SecurityGuardDTO securityGuardDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(securityGuardDTO, SecurityGuard.class);
    }
}
