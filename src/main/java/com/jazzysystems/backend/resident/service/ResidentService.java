package com.jazzysystems.backend.resident.service;

import java.util.List;

import com.jazzysystems.backend.apartment.Apartment;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.resident.Resident;
import com.jazzysystems.backend.resident.dto.ResidentDTO;

public interface ResidentService {

    Resident saveResident(ResidentDTO residentDTO);

    Resident updateResident(Long residentId, ResidentDTO residentDTO);

    void deleteResident(Resident resident);

    void deleteResidentById(Long residentId);

    Resident findById(Long residentId);

    List<Resident> findAll();

    Resident findByPerson(Person person);

    Boolean ExistsByApartmentAndIsRepresentative(Boolean isRepresentative, Apartment apartment);

}
