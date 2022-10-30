package com.jazzysystems.backend.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.repository.PersonRepository;

@Component
public class Authentication {

    @Autowired
    private PersonRepository personRepository;

    public String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Person getAuthenticatedPerson() {
        Optional<Person> optionalPerson = personRepository.findByEmail(this.getUserName());
        return optionalPerson.get();
    }
}
