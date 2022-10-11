package com.jazzysystems.backend.resident.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jazzysystems.backend.exception.NoSuchElementFoundException;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.resident.Resident;
import com.jazzysystems.backend.resident.ResidentMapper;
import com.jazzysystems.backend.resident.dto.ResidentDTO;
import com.jazzysystems.backend.resident.repository.ResidentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResidentServiceImpl implements ResidentService {

    private final ResidentRepository residentRepository;

    @Autowired
    ResidentMapper residentMapper;

    @Override
    public Resident saveResident(ResidentDTO residentDTO) {
        Resident resident = residentMapper.convertDTOtoResident(residentDTO);
        return residentRepository.save(resident);
    }

    @Override
    public Resident updateResident(Long residentId, ResidentDTO residentDTO) {
        Resident resident = this.findById(residentId);
        resident.setApartment(residentDTO.getApartment());
        resident.setPerson(residentDTO.getPerson());
        resident.setIsRepresentative(residentDTO.getIsRepresentative());
        resident.setIsResident(residentDTO.getIsResident());
        return residentRepository.save(resident);
    }

    @Override
    public void deleteResident(Resident resident) {
        residentRepository.delete(resident);
    }

    @Override
    public void deleteResidentById(Long residentId) {
        residentRepository.deleteById(residentId);
    }

    @Override
    public Resident findById(Long residentId) {
        Resident resident = residentRepository.findById(residentId).orElseThrow(
                () -> new NoSuchElementFoundException("Not Found"));
        return resident;
    }

    @Override
    public List<Resident> findAll() {
        return residentRepository.findAll();
    }

    @Override
    public Resident findByPerson(Person person) {
        Resident resident = residentRepository.findByPerson(person).orElseThrow(
                () -> new NoSuchElementFoundException("Not Found"));
        return resident;
    }

}
