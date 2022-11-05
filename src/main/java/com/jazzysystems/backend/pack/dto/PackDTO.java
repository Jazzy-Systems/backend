package com.jazzysystems.backend.pack.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.jazzysystems.backend.person.dto.PersonDTO;
import com.jazzysystems.backend.securityguard.dto.SecurityGuardDTO;

import lombok.Data;

@Data
public class PackDTO {

    private Long packId;

    @NotBlank
    private PersonDTO personDTO;

    private SecurityGuardDTO securityGuardDTO;
    
    private String messengerName;

    private String typePack;

    private String observation;

    private Boolean received;

    private LocalDateTime datePickedUp;

    private LocalDateTime dateArrival;
}

