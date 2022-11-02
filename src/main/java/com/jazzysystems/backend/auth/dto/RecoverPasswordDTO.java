package com.jazzysystems.backend.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RecoverPasswordDTO {
    private String email;

    private String currentPassword;

    private String newPassword;
}
