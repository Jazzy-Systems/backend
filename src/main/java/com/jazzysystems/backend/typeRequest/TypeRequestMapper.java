package com.jazzysystems.backend.typeRequest;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jazzysystems.backend.typeRequest.dto.TypeRequestDTO;

@Component
public class TypeRequestMapper {
    public TypeRequestDTO convertToDTO(TypeRequest typeRequest) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(typeRequest, TypeRequestDTO.class);
    }

    public TypeRequest convertDTOtoObject(TypeRequestDTO typeRequestDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(typeRequestDTO, TypeRequest.class);
    }
}
