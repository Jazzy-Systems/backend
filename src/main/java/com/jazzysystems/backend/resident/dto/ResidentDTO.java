package com.jazzysystems.backend.resident.dto;

import com.jazzysystems.backend.apartment.Apartment;
import com.jazzysystems.backend.person.Person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResidentDTO {

    private Long residentId;

    private Person person;

    private Apartment apartment;

    private Boolean isRepresentative;

    private Boolean isResident;

    public ResidentDTO(Person person, Apartment apartment, Boolean isRepresentative, Boolean isResident) {
        this.person = person;
        this.apartment = apartment;
        this.isRepresentative = isRepresentative;
        this.isResident = isResident;
    }

}
