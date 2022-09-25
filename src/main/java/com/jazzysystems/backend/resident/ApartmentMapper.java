package com.jazzysystems.backend.resident;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ApartmentMapper {

    public ApartmentDTO convertApartmentToDTO(Apartment apartment) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(apartment, ApartmentDTO.class);
    }

    public Apartment convertDTOtoApartment(ApartmentDTO apartmentDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(apartmentDTO, Apartment.class);
    }
}
