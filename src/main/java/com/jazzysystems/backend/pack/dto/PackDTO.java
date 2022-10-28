package com.jazzysystems.backend.pack.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.securityguard.SecurityGuard;

import lombok.Data;

@Data
public class PackDTO {
    private Long packId;

    @NotBlank
    private Person person;

    @NotBlank
    private SecurityGuard securityGuard;

    private String messengerName;

    private String typePack;

    private String observation;

    private Boolean received;

    private LocalDateTime datePickedUp;

    private LocalDateTime dateArrival;
}

