package com.jazzysystems.backend.person;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jazzysystems.backend.person.dto.PersonDTO;

@Component
public class PersonMapper {

    public PersonDTO convertPersonToDTO(Person person) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(person, PersonDTO.class);
    }

    public Person convertDTOtoPerson(PersonDTO personDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(personDTO, Person.class);
    }
}
