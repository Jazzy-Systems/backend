package com.jazzysystems.backend.apartment.controller;

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

import com.jazzysystems.backend.apartment.Apartment;
import com.jazzysystems.backend.apartment.dto.ApartmentDTO;
import com.jazzysystems.backend.apartment.service.ApartmentService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/apartment")
public class ApartmentController {

    private final ApartmentService apartmentService;

    @GetMapping(value = "")
    public ResponseEntity<?> findAllApartments() {
        return new ResponseEntity<>(apartmentService.findAll(), HttpStatus.OK);
    }

    @GetMapping({ "/{apartmentId}" })
    public ResponseEntity<?> findApartment(@PathVariable Long apartmentId) {
        Apartment apartment = apartmentService.findApartmentById(apartmentId);
        return new ResponseEntity<Apartment>(apartment, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> saveApartment(@RequestBody ApartmentDTO apartmentDTO) {
        Apartment apartment = apartmentService.saveApartment(apartmentDTO);
        return new ResponseEntity<Apartment>(apartment, HttpStatus.CREATED);
    }

    @PutMapping({ "/{apartmentId}" })
    public ResponseEntity<?> updateApartment(@PathVariable("apartmentId") Long apartmentId,
            @RequestBody ApartmentDTO apartmentDTO) {
        Apartment apartment = apartmentService.updateApartment(apartmentId, apartmentDTO);
        return new ResponseEntity<>(apartment, HttpStatus.OK);
    }

    @DeleteMapping({ "/{apartmentId}" })
    public ResponseEntity<?> deleteApartmentById(@PathVariable("apartmentId") Long apartmentId) {
        apartmentService.deleteApartmentById(apartmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
