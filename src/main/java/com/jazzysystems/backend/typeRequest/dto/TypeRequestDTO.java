package com.jazzysystems.backend.typeRequest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TypeRequestDTO {

    Long id;

    String typeRequestName;

    public TypeRequestDTO(String typeRequestName) {
        this.typeRequestName = typeRequestName;
    }
}
