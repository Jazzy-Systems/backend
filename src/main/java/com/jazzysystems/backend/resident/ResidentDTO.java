package com.jazzysystems.backend.resident;

import com.jazzysystems.backend.person.Person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ResidentDTO {

    private Long residentId;

    private Person person;

    private Apartment apartment;

    public ResidentDTO(Person person, Apartment apartment) {
        this.person = person;
        this.apartment = apartment;
    }
}
