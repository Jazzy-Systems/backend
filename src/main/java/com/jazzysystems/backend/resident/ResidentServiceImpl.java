package com.jazzysystems.backend.resident;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
