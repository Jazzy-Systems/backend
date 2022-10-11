package com.jazzysystems.backend.securityguard.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jazzysystems.backend.company.Company;
import com.jazzysystems.backend.exception.NoSuchElementFoundException;
import com.jazzysystems.backend.person.Person;
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

    @Override
    public SecurityGuard updateSecurityGuard(Long securityGuardId, SecurityGuardDTO securityGuardDTO) {
        SecurityGuard securityGuard = this.findsecurityGuardById(securityGuardId);
        securityGuard.setIsActive(securityGuardDTO.getIsActive());
        securityGuard.setCompany(securityGuard.getCompany());
        securityGuard.setPerson(securityGuard.getPerson());
        return securityGuardRepository.save(securityGuard);
    }

    @Override
    public void deleteSecurityGuard(SecurityGuardDTO securityGuardDTO) {
        SecurityGuard securityGuard = securityGuardMapper.convertDTOtoSecurityGuard(securityGuardDTO);
        securityGuardRepository.delete(securityGuard);
    }

    @Override
    public void deleteSecurityGuardById(Long securityGuardId) {
        securityGuardRepository.deleteById(securityGuardId);
    }

    @Override
    public SecurityGuard findsecurityGuardById(Long securityGuardId) {
        SecurityGuard securityGuard = securityGuardRepository.findById(securityGuardId).orElseThrow(
                () -> new NoSuchElementFoundException("Not Found"));
        return securityGuard;
    }

    @Override
    public List<SecurityGuard> findAll() {
        return securityGuardRepository.findAll();
    }

    @Override
    public SecurityGuard findByCompany(Company company) {
        SecurityGuard securityGuard = securityGuardRepository.findByCompany(company).orElseThrow(
                () -> new NoSuchElementFoundException("Not Found"));
        return securityGuard;
    }

    @Override
    public Boolean existsById(Long securityGuardId) {
        return securityGuardRepository.existsById(securityGuardId);
    }

    @Override
    public SecurityGuard findByPerson(Person person) {
        SecurityGuard securityGuard = securityGuardRepository.findByPerson(person).orElseThrow(
                () -> new NoSuchElementFoundException("Not Found"));
        return securityGuard;
    }

}
