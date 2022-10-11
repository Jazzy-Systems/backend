package com.jazzysystems.backend.apartment.service;

import java.util.List;

import com.jazzysystems.backend.apartment.Apartment;
import com.jazzysystems.backend.apartment.dto.ApartmentDTO;

public interface ApartmentService {

    Apartment saveApartment(ApartmentDTO apartmentDTO);

    Apartment updateApartment(Long apartmentId, ApartmentDTO apartmentDTO);

    void deleteApartment(ApartmentDTO apartmentDTO);

    void deleteApartmentById(Long apartmentId);

    Apartment findApartmentById(Long apartmentId);

    List<Apartment> findAll();

    Apartment findByBuildingNameAndNumber(
            String buildingName, String number);

    Boolean existsById(Long apartmentId);

    Apartment findByCodeApartment(
            String codeApartment);

}
