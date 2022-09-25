package com.jazzysystems.backend.auth.pojo;

import com.jazzysystems.backend.person.PersonDTO;
import com.jazzysystems.backend.resident.ApartmentDTO;

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
    private String enabled;

    // Person
    private PersonDTO personDTO;

    // ROl
    private String roleName;

    // Apartment
    private ApartmentDTO apartmentDTO;

    // SecurityGuard
    private String companyName;

}
