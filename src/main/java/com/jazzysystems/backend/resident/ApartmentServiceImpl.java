package com.jazzysystems.backend.resident;

import org.springframework.stereotype.Service;

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
