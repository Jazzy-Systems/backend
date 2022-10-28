package com.jazzysystems.backend.pack;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jazzysystems.backend.pack.dto.PackDTO;


@Component

public class PackMapper {
    public PackDTO convertPackToDTO(Pack pack) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(pack, PackDTO.class);
    }

    public Pack convertDTOtoPack(PackDTO packDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(packDTO, Pack.class);
    }
}
