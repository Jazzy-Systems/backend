package com.jazzysystems.backend.securityguard.service;

import org.springframework.stereotype.Service;

import com.jazzysystems.backend.securityguard.SecurityGuard;
import com.jazzysystems.backend.securityguard.SecurityGuardMapper;
import com.jazzysystems.backend.securityguard.dto.SecurityGuardDTO;
import com.jazzysystems.backend.securityguard.repository.SecurityGuardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityGuardImpl implements SecurityGuardService {

    private final SecurityGuardRepository securityGuardRepository;

    private final SecurityGuardMapper securityGuardMapper;

    @Override
    public SecurityGuard saveSecurityGuard(SecurityGuardDTO securityGuardDTO) {
        SecurityGuard securityGuard = securityGuardMapper.convertDTOtoSecurityGuard(securityGuardDTO);
        return securityGuardRepository.save(securityGuard);
    }

}
