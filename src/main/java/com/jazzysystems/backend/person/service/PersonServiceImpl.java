package com.jazzysystems.backend.person.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jazzysystems.backend.auth.Authentication;
import com.jazzysystems.backend.exception.NoSuchElementFoundException;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.PersonMapper;
import com.jazzysystems.backend.person.dto.PersonDTO;
import com.jazzysystems.backend.person.repository.PersonRepository;
import com.jazzysystems.backend.resident.Resident;
import com.jazzysystems.backend.resident.service.ResidentService;
import com.jazzysystems.backend.util.emailSender.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    @Autowired
    private final Authentication authentication;
    
    @Autowired
    private final ResidentService residentService;

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public List<Person> findAllResident(Long apartmentId) {
        List <Resident> listResident = residentService.findByAparmentResident(apartmentId);  
        List <Person> listPerson = new ArrayList <Person>();
        for(Resident resident: listResident){
            listPerson.add(findById(resident.getPerson().getPersonId()));
        }
        return listPerson;
    }


    @Override
    public Person findPersonByDni(long dni) {
        Person person = personRepository.findPersonByDni(dni).orElseThrow(
                () -> new NoSuchElementFoundException("DNI Person Not Found"));
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
        person.setPhone(personDTO.getPhone());
        person.setEmail(personDTO.getEmail());
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

    @Override
    public void deletePersonByDNI(Long persoDNI) {
        Long personId = this.findPersonByDni(persoDNI).getPersonId();
        personRepository.deleteById(personId);
    }

    @Override
    public Person findPersonByEmail(String email) {
        Person person = personRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementFoundException("Person Not Found"));
        return person;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return personRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByDni(Long dni) {
        return personRepository.existsByDni(dni);
    }

    @Override
    public Person updatePhone(Long phone) {
        Person person = authentication.getAuthenticatedPerson();
        person.setPhone(phone);
        return personRepository.save(person);
    }

    @Override
    public Person getPerson() {
        return authentication.getAuthenticatedPerson();
    }
}
