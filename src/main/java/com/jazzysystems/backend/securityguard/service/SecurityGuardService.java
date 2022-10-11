package com.jazzysystems.backend.securityguard.service;

import com.jazzysystems.backend.company.Company;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.securityguard.SecurityGuard;
import com.jazzysystems.backend.securityguard.dto.SecurityGuardDTO;

public interface SecurityGuardService {
    
    SecurityGuard saveSecurityGuard(SecurityGuardDTO securityGuardDTO);

    SecurityGuard updateSecurityGuard(Long securityGuardId, SecurityGuardDTO securityGuardDTO);

    void deleteSecurityGuard(SecurityGuardDTO securityGuardDTO);

    void deleteSecurityGuardById(Long securityGuardId);

    SecurityGuard findsecurityGuardById(Long securityGuardId);

    List<SecurityGuard> findAll();

    SecurityGuard findByCompany(Company company);

    Boolean existsById(Long securityGuardId);

    SecurityGuard findByPerson(Person person);

}
