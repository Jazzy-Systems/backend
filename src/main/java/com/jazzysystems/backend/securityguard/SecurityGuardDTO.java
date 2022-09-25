package com.jazzysystems.backend.securityguard;

import com.jazzysystems.backend.person.PersonDTO;

import lombok.Data;

@Data
public class SecurityGuardDTO {

    private Long securityGuardId;

    private CompanyDTO companyDTO;

    private PersonDTO personDTO;
}
