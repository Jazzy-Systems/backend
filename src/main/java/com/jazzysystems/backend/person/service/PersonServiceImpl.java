package com.jazzysystems.backend.person.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jazzysystems.backend.exception.NoSuchElementFoundException;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.PersonMapper;
import com.jazzysystems.backend.person.dto.PersonDTO;
import com.jazzysystems.backend.person.repository.PersonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person findPersonByDni(long dni) {
        Person person = personRepository.findPersonByDni(dni).orElseThrow(
                () -> new NoSuchElementFoundException("Not Found"));
        return person;
    }

    @Override
    public Boolean existsById(Long personId) {
        return personRepository.existsById(personId);
    }

    @Override
    public Person findById(Long personId) {
        Person person = personRepository.findById(personId).orElseThrow(
                () -> new NoSuchElementFoundException("Not Found"));
        return person;
    }

    @Override
    public Person savePerson(PersonDTO personDTO) {
        Person person = personMapper.convertDTOtoPerson(personDTO); // person without Id
        Optional<Person> optionalPerson = personRepository.findPersonByDni(personDTO.getDni());
        if (optionalPerson.isPresent()) {
            person.setPersonId(optionalPerson.get().getPersonId());
        }
        // if person exits update otherwise will save a new Person
        return personRepository.save(person);
    }

}
