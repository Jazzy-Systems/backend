package com.jazzysystems.backend.apartment.service;

import java.util.List;

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
    public Apartment findByCodeApartment(String codeApartment){
        Apartment apartment = apartmentRepository.finbByCodeApartment(codeApartment).orElseThrow(
                () -> new NoSuchElementFoundException("Not Found"));
        return apartment;
    }

}
