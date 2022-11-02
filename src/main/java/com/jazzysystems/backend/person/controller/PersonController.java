package com.jazzysystems.backend.person.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.dto.PersonDTO;
import com.jazzysystems.backend.person.service.PersonService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/person/")
public class PersonController {

    private final PersonService personService;

    @GetMapping(value = "")
    public ResponseEntity<?> findAllPersons() {
        return new ResponseEntity<>(personService.findAll(), HttpStatus.OK);
    }

    @GetMapping({ "/{personId}" })
    public ResponseEntity<?> findPerson(@PathVariable Long personId) {
        Person person = personService.findById(personId);
        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> savePerson(@RequestBody PersonDTO personDTO) {
        Person person = personService.savePerson(personDTO);
        return new ResponseEntity<Person>(person, HttpStatus.CREATED);
    }

    @PutMapping({ "/{personId}" }) // admin y persona
    public ResponseEntity<?> updatePerson(@PathVariable("personId") Long personId,
            @RequestBody PersonDTO personDTO) {
        Person person = personService.updatePerson(personId, personDTO);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PutMapping({ "/update/{phone}" })
    public ResponseEntity<?> updatePersonProfile(
            @PathVariable("phone") Long phone) {
        Person person = personService.updatePhone(phone);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @DeleteMapping({ "/{personId}" })
    public ResponseEntity<?> deletePersonById(@PathVariable("personId") Long personId) {
        personService.deletePersonById(personId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping({ "/dni/{dni}" })
    public ResponseEntity<?> findByDni(@PathVariable Long dni) {
        Person person = personService.findPersonByDni(dni);
        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    /**
     * get the data of the authenticated person
     * 
     * @return Person
     */
    @GetMapping({ "/myprofile/" })
    public ResponseEntity<?> getPerson() {
        Person person = personService.getPerson();
        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

}
