package com.jazzysystems.backend.resident;

public interface ApartmentService {

    Apartment saveApartment(ApartmentDTO apartmentDTO);

    Apartment findByBuildingNameAndNumber(
            String buildingName, String number);
}
