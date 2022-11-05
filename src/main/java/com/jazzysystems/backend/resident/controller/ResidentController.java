package com.jazzysystems.backend.resident.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jazzysystems.backend.resident.Resident;
import com.jazzysystems.backend.resident.dto.ResidentDTO;
import com.jazzysystems.backend.resident.service.ResidentService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/resident")
public class ResidentController {

    @Autowired
    private final ResidentService residentService;

    @GetMapping(value = "")
    public ResponseEntity<?> findAllAResidents() {
        return new ResponseEntity<>(residentService.findAll(), HttpStatus.OK);
    }
 
    @GetMapping({ "/{residentId}" })
    public ResponseEntity<?> findResident(@PathVariable Long residentId) {
        Resident resident = residentService.findById(residentId);
        return new ResponseEntity<Resident>(resident, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> saveResident(@RequestBody ResidentDTO residentDTO) {
        Resident resident = residentService.saveResident(residentDTO);
        return new ResponseEntity<Resident>(resident, HttpStatus.CREATED);
    }

    @PutMapping({ "/{residentId}" })
    public ResponseEntity<?> updateResident(@PathVariable("residentId") Long residentId,
            @RequestBody ResidentDTO residentDTO) {
        Resident resident = residentService.updateResident(residentId, residentDTO);
        return new ResponseEntity<>(resident, HttpStatus.OK);
    }
}
