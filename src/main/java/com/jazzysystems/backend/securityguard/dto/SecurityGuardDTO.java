package com.jazzysystems.backend.securityguard.dto;

import com.jazzysystems.backend.company.Company;
import com.jazzysystems.backend.person.Person;

import lombok.Data;

@Data
public class SecurityGuardDTO {

    private Long securityGuardId;
    
    private Company company;
    
    private Person person;

    public SecurityGuardDTO(Person person, Company company) {
        this.person = person;
        this.company = company;
    }

}
