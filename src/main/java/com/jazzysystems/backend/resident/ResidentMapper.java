package com.jazzysystems.backend.resident;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jazzysystems.backend.resident.dto.ResidentDTO;

@Component
public class ResidentMapper {

    public ResidentDTO convertResidentToDTO(Resident resident) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(resident, ResidentDTO.class);
    }

    public Resident convertDTOtoResident(ResidentDTO residentDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(residentDTO, Resident.class);
    }
}
