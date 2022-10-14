package com.jazzysystems.backend.apartment.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ApartmentDTO {

    private Long apartmentId;

    @NotBlank
    private String buildingName;

    @NotBlank
    private String apartmentNumber;
}
