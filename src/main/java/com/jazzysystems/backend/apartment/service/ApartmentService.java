package com.jazzysystems.backend.apartment.service;

import com.jazzysystems.backend.apartment.Apartment;
import com.jazzysystems.backend.apartment.dto.ApartmentDTO;

public interface ApartmentService {

    Apartment saveApartment(ApartmentDTO apartmentDTO);

    Apartment findByBuildingNameAndNumber(
            String buildingName, String number);
}
