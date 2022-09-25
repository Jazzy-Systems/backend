package com.jazzysystems.backend.resident.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
