package com.jazzysystems.backend.resident.service;

import com.jazzysystems.backend.resident.Resident;
import com.jazzysystems.backend.resident.dto.ResidentDTO;

public interface ResidentService {

    Resident saveResident(ResidentDTO residentDTO);
}
