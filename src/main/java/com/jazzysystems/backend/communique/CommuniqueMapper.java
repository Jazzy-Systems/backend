package com.jazzysystems.backend.communique;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jazzysystems.backend.communique.dto.CommuniqueDTO;

@Component
public class CommuniqueMapper {

    public CommuniqueDTO convertCommuniqueToDTO(Communique communique) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(communique, CommuniqueDTO.class);
    }

    public Communique convertDTOtoCommunique(CommuniqueDTO communiqueDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(communiqueDTO, Communique.class);
    }
}
