package com.jazzysystems.backend.securityguard.dto;

import com.jazzysystems.backend.company.dto.CompanyDTO;
import com.jazzysystems.backend.person.Person;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityGuardDTO {

    private Long securityGuardId;
    
    private CompanyDTO companyDTO;
    
    private Person person;

    private Boolean isActive;

    public SecurityGuardDTO(Person person, CompanyDTO companyDTO) {
        this.person = person;
        this.companyDTO = companyDTO;
    }

}
