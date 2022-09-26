package com.jazzysystems.backend.company.service;

import java.util.List;

import com.jazzysystems.backend.company.Company;
import com.jazzysystems.backend.company.dto.CompanyDTO;

public interface CompanyService {

    Company saveCompany(CompanyDTO companyDTO);

    Company updateCompany(Long companyId, CompanyDTO companyDTO);

    void deleteCompany(CompanyDTO companyDTO);

    void deleteCompanyById(Long companyId);

    Company findCompanyById(Long companyId);

    List<Company> findAll();

    Company findByCompanyName(
            String companyName);

    Boolean existsById(Long companyId);
}
