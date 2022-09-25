package com.jazzysystems.backend.auth.pojo;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class LoginPOJO {
    @NotBlank
    private final String email;
    @NotBlank
    private final String password;
}
