package com.jazzysystems.backend.request;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jazzysystems.backend.request.dto.RequestDTO;

@Component
public class RequestMapper {
    public RequestDTO convertRequestToDTO(Request request) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(request, RequestDTO.class);
    }

    public Request convertDTOtoRequest(RequestDTO requestDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(requestDTO, Request.class);
    }
}
