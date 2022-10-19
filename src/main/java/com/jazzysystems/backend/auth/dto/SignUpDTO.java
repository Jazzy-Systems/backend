package com.jazzysystems.backend.auth.dto;

import javax.validation.constraints.NotBlank;

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
public class SignUpDTO {
    // User
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    // ROl
    private String roleName;

    // code of Apartment or Company
    private String code;
}
