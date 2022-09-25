package com.jazzysystems.backend.securityguard.dto;

import com.jazzysystems.backend.company.dto.CompanyDTO;
import com.jazzysystems.backend.person.dto.PersonDTO;

import lombok.Data;

@Data
public class SecurityGuardDTO {

    private Long securityGuardId;

    private CompanyDTO companyDTO;

    private PersonDTO personDTO;
}
