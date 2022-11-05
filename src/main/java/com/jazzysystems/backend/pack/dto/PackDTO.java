package com.jazzysystems.backend.pack.dto;

import javax.validation.constraints.NotBlank;

import com.jazzysystems.backend.person.dto.PersonDTO;

import lombok.Data;

@Data
public class PackDTO {

    private Long packId;

    @NotBlank
    private PersonDTO personDTO;

    private String messengerName;

    private String typePack;

    private String observation;
}

