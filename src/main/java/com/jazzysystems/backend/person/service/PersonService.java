package com.jazzysystems.backend.person.service;

import java.util.List;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.dto.PersonDTO;

public interface PersonService {
    List<Person> getAllPersons();

    Person findPersonByDni(long dni);

    Boolean existsById(Long personId);

    Person findById(Long personId);

    Person savePerson(PersonDTO personDTO);
}
