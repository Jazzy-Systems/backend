package com.jazzysystems.backend.company.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jazzysystems.backend.company.Company;
import com.jazzysystems.backend.company.CompanyMapper;
import com.jazzysystems.backend.company.dto.CompanyDTO;
import com.jazzysystems.backend.company.repository.CompanyRepository;
import com.jazzysystems.backend.exception.NoSuchElementFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    @Override
    public Company saveCompany(CompanyDTO companyDTO) {
        Company company = companyMapper.convertDTOtoCompany(companyDTO);
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(Long companyId, CompanyDTO companyDTO) {
        Company company = this.findCompanyById(companyId);
        company.setCompanyName(companyDTO.getCompanyName());
        company.setPhone(companyDTO.getPhone());
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(CompanyDTO companyDTO) {
        Company company = companyMapper.convertDTOtoCompany(companyDTO);
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

}
