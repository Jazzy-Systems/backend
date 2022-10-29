package com.jazzysystems.backend.request.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.typeRequest.TypeRequest;

import lombok.Data;

@Data
public class RequestDTO {
    private Long requestId;

    @NotBlank
    private Person person;

    @NotBlank
    private TypeRequest typeRequest;

    @NotBlank
    private String titleRequest;

    @NotBlank
    private String descriptionRequest;

    private String responseRequest;

    private LocalDateTime dateRequest;

    private LocalDateTime dateResponse;
    
    private Boolean statusRequest;
}
