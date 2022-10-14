package com.jazzysystems.backend.apartment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jazzysystems.backend.apartment.Apartment;
import com.jazzysystems.backend.apartment.ApartmentMapper;
import com.jazzysystems.backend.apartment.dto.ApartmentDTO;
import com.jazzysystems.backend.apartment.repository.ApartmentRepository;
import com.jazzysystems.backend.exception.NoSuchElementFoundException;
import com.jazzysystems.backend.util.SecurityCodeGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;

    private final ApartmentMapper apartmentMapper;

    @Override
    public Apartment saveApartment(ApartmentDTO apartmentDTO) {
        Apartment apartment = apartmentMapper.convertDTOtoApartment(apartmentDTO);
        apartment.setCodeApartment(this.generateCodeApartment());
        return apartmentRepository.save(apartment);
    }

    @Override
    public Apartment findByBuildingNameAndNumber(String buildingName, String number) {
        Apartment apartment = apartmentRepository.findByBuildingNameAndApartmentNumber(buildingName, number)
                .orElseThrow(
                        () -> new NoSuchElementFoundException("Not Found"));
        return apartment;
    }

    @Override
    public Apartment updateApartment(Long apartmentId, ApartmentDTO apartmentDTO) {
        Apartment apartment = this.findApartmentById(apartmentId);
        apartment.setBuildingName(apartmentDTO.getBuildingName());
        apartment.setApartmentNumber(apartmentDTO.getNumber());
        return apartmentRepository.save(apartment);
    }

    @Override
    public void deleteApartment(ApartmentDTO apartmentDTO) {
        Apartment apartment = apartmentMapper.convertDTOtoApartment(apartmentDTO);
        apartmentRepository.delete(apartment);
    }

    @Override
    public void deleteApartmentById(Long apartmentId) {
        apartmentRepository.deleteById(apartmentId);
    }

    @Override
    public Apartment findApartmentById(Long apartmentId) {
        Apartment apartment = apartmentRepository.findById(apartmentId).orElseThrow(
                () -> new NoSuchElementFoundException("Not Found"));
        return apartment;
    }

    @Override
    public List<Apartment> findAll() {
        return apartmentRepository.findAll();
    }

    @Override
    public Boolean existsById(Long apartmentId) {
        return apartmentRepository.existsById(apartmentId);
    }

    @Override
    public Apartment findByCodeApartment(String codeApartment) {
        Apartment apartment = apartmentRepository.findByCodeApartment(codeApartment).orElseThrow(
                () -> new NoSuchElementFoundException("Not Found"));
        return apartment;
    }

    public String generateCodeApartment() {
        SecurityCodeGenerator securityCodeGenerator = new SecurityCodeGenerator();
        String total = Long.toString(apartmentRepository.count());
        String codeApartment = securityCodeGenerator.generateCode(total);
        int limit = 50;
        int i = 0;
        while (this.apartmentRepository.existsByCodeApartment(codeApartment) && i < limit) {
            codeApartment = securityCodeGenerator.generateCode(total);
            i++;
        }
        return codeApartment;
    }
}
