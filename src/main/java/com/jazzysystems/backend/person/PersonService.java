package com.jazzysystems.backend.person;

import java.util.List;

public interface PersonService {
    List<Person> getAllPersons();

    Person findPersonByDni(long dni);

    Boolean existsById(Long personId);

    Person findById(Long personId);

    Person savePerson(PersonDTO personDTO);
}
