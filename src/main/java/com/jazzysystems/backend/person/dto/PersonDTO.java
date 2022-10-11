package com.jazzysystems.backend.person.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    private long personId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private long dni;

    private long phone;

    @NotBlank
    private String email;

    public PersonDTO(@NotBlank String firstName, @NotBlank String lastName, @NotBlank long dni, long phone,
            @NotBlank String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.phone = phone;
    }

}
