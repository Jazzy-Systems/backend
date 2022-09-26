package com.jazzysystems.backend.company.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jazzysystems.backend.company.Company;
import com.jazzysystems.backend.company.dto.CompanyDTO;
import com.jazzysystems.backend.company.service.CompanyService;

import lombok.RequiredArgsConstructor;

//TODO preautorized(admin for all methods? or could the resident have acces to
//few of them)
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping(value = "")
    public ResponseEntity<?> findAllCompanies() {
        return new ResponseEntity<>(companyService.findAll(), HttpStatus.OK);
    }

    @GetMapping({ "/{companyId}" })
    public ResponseEntity<?> findCompany(@PathVariable Long companyId) {
        Company company = companyService.findCompanyById(companyId);
        return new ResponseEntity<Company>(company, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> saveCompany(@RequestBody CompanyDTO companyDTO) {
        Company company = companyService.saveCompany(companyDTO);
        return new ResponseEntity<Company>(company, HttpStatus.CREATED);
    }

    @PutMapping({ "/{companyId}" })
    public ResponseEntity<?> updateCompany(@PathVariable("companyId") Long companyId,
            @RequestBody CompanyDTO companyDTO) {
        Company company = companyService.updateCompany(companyId, companyDTO);
        return new ResponseEntity<Company>(company, HttpStatus.OK);
    }

    @DeleteMapping({ "/{companyId}" })
    public ResponseEntity<?> deleteCompanyById(@PathVariable("companyId") Long companyId) {
        companyService.deleteCompanyById(companyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
