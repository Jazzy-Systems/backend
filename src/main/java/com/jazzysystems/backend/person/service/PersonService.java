package com.jazzysystems.backend.person.service;

import java.util.List;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.dto.PersonDTO;

public interface PersonService {

    Person savePerson(PersonDTO personDTO);

    Person updatePerson(Long personId, PersonDTO personDTO);

    Person findPersonByDni(long dni);

    Person findById(Long personId);

    Boolean existsById(Long personId);

    void deletePerson(PersonDTO personDTO);

    void deletePersonById(Long personId);

    List<Person> findAll();

    Person findPersonByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByDni(Long dni);

    Person updatePhone(Long phone);

    Person getPerson();
}
