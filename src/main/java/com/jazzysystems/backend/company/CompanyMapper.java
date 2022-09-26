package com.jazzysystems.backend.company;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jazzysystems.backend.company.dto.CompanyDTO;

@Component
public class CompanyMapper {

    public CompanyDTO convertCompanyToDTO(Company company) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(company, CompanyDTO.class);
    }

    public Company convertDTOtoCompany(CompanyDTO companyDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(companyDTO, Company.class);
    }
}
