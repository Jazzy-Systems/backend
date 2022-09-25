package com.jazzysystems.backend.apartment.service;

import org.springframework.stereotype.Service;

import com.jazzysystems.backend.apartment.Apartment;
import com.jazzysystems.backend.apartment.ApartmentMapper;
import com.jazzysystems.backend.apartment.dto.ApartmentDTO;
import com.jazzysystems.backend.apartment.repository.ApartmentRepository;
import com.jazzysystems.backend.exception.NoSuchElementFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;

    private final ApartmentMapper apartmentMapper;

    @Override
    public Apartment saveApartment(ApartmentDTO apartmentDTO) {
        Apartment apartment = apartmentMapper.convertDTOtoApartment(apartmentDTO);
        return apartmentRepository.save(apartment);
    }

    @Override
    public Apartment findByBuildingNameAndNumber(String buildingName, String number) {
        Apartment apartment = apartmentRepository.findByBuildingNameAndNumber(buildingName, number).orElseThrow(
                () -> new NoSuchElementFoundException("Not Found"));
        return apartment;
    }

}
