package com.jazzysystems.backend.pack;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jazzysystems.backend.pack.dto.PackDTO;
import com.jazzysystems.backend.person.PersonMapper;
import com.jazzysystems.backend.securityguard.SecurityGuardMapper;


@Component

public class PackMapper {

    @Autowired
    private SecurityGuardMapper securityGuardMapper;

    @Autowired
    private PersonMapper personMapper;

    public PackDTO convertPackToDTO(Pack pack) {
        ModelMapper modelMapper = new ModelMapper();
        PackDTO packDTO = modelMapper.map(pack, PackDTO.class);
        packDTO.setSecurityGuardDTO(securityGuardMapper.convertSecurityGuardToDTO(pack.getSecurityGuard()));        
        packDTO.setPersonDTO(personMapper.convertPersonToDTO(pack.getPerson()));
        return packDTO;
    }

    public Pack convertDTOtoPack(PackDTO packDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(packDTO, Pack.class);
    }
}
