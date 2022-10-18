package com.jazzysystems.backend.company.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jazzysystems.backend.company.Company;
import com.jazzysystems.backend.company.CompanyMapper;
import com.jazzysystems.backend.company.dto.CompanyDTO;
import com.jazzysystems.backend.company.repository.CompanyRepository;
import com.jazzysystems.backend.exception.NoSuchElementFoundException;
import com.jazzysystems.backend.util.SecurityCodeGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    @Override
    public Company saveCompany(CompanyDTO companyDTO) {
        Company company = companyMapper.convertDTOtoCompany(companyDTO);
        company.setCodeCompany(this.generateCodeCompany());
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(Long companyId, CompanyDTO companyDTO) {
        Company company = this.findCompanyById(companyId);
        company.setCompanyName(companyDTO.getCompanyName());
        company.setCompanyEmail(companyDTO.getCompanyEmail());
        company.setPhone(companyDTO.getPhone());
        company.setNit(companyDTO.getNit());
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(CompanyDTO companyDTO) {
        Company company = companyMapper.convertDTOtoCompany(companyDTO);
        company.setCodeCompany(null);
        companyRepository.delete(company);
    }

    @Override
    public void deleteCompanyById(Long companyId) {
        companyRepository.deleteById(companyId);
    }

    @Override
    public Company findCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(
                () -> new NoSuchElementFoundException("Company Not Found"));
        return company;
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company findByCompanyName(String companyName) {
        Company company = companyRepository.findByCompanyName(companyName).orElseThrow(
                () -> new NoSuchElementFoundException("Company Not Found"));
        return company;
    }

    @Override
    public Boolean existsById(Long companyId) {
        return companyRepository.existsById(companyId);
    }

    @Override
    public Company finbByCodeCompany(String codeCompany) {
        Company company = companyRepository.findByCodeCompany(codeCompany).orElseThrow(
                () -> new NoSuchElementFoundException("Company Not Found"));
        return company;
    }

    public String generateCodeCompany() {
        SecurityCodeGenerator securityCodeGenerator = new SecurityCodeGenerator();
        String total = Long.toString(companyRepository.count());
        String codeCompany = securityCodeGenerator.generateCode(total);
        int limit = 50;
        int i = 0;
        while (this.companyRepository.existsByCodeCompany(codeCompany) && i < limit) {
            codeCompany = securityCodeGenerator.generateCode(total);
            i++;
        }
        return codeCompany;
    }

}
