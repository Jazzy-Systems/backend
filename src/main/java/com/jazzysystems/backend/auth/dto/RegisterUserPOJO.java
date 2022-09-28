package com.jazzysystems.backend.auth.dto;

import com.jazzysystems.backend.apartment.dto.ApartmentDTO;
import com.jazzysystems.backend.person.dto.PersonDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserPOJO {

    // User
    private String email;
    private String password;
    private String isEnabled;

    // Person
    private PersonDTO personDTO;

    // ROl
    private String roleName;

    // Apartment
    private ApartmentDTO apartmentDTO;

    // SecurityGuard
    private String companyName;

}
