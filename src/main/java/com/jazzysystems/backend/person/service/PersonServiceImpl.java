package com.jazzysystems.backend.person.service;

import java.util.List;

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
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person findPersonByDni(long dni) {
        Person person = personRepository.findPersonByDni(dni).orElseThrow(
                () -> new NoSuchElementFoundException("Person Not Found"));
        return person;
    }

    @Override
    public Boolean existsById(Long personId) {
        return personRepository.existsById(personId);
    }

    @Override
    public Person findById(Long personId) {
        Person person = personRepository.findById(personId).orElseThrow(
                () -> new NoSuchElementFoundException("Person Not Found"));
        return person;
    }

    @Override
    public Person savePerson(PersonDTO personDTO) {
        Person person = personMapper.convertDTOtoPerson(personDTO);
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(Long personId, PersonDTO personDTO) {
        Person person = this.findById(personId);
        person.setDni(personDTO.getDni());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setPhone(personDTO.getPersonId());
        return personRepository.save(person);
    }

    @Override
    public void deletePerson(PersonDTO personDTO) {
        Person person = personMapper.convertDTOtoPerson(personDTO);
        personRepository.delete(person);
    }

    @Override
    public void deletePersonById(Long personId) {
        personRepository.deleteById(personId);
    }

}
