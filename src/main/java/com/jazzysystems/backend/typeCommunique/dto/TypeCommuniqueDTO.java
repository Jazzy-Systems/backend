package com.jazzysystems.backend.typeCommunique.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TypeCommuniqueDTO {

    private Long typeCommuniqueId;

    private String typeName;

    public TypeCommuniqueDTO(String typeName) {
        this.typeName = typeName;
    }
}
