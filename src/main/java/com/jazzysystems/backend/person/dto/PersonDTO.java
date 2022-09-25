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
}
