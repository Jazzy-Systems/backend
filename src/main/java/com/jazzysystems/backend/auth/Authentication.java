package com.jazzysystems.backend.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.repository.PersonRepository;
import com.jazzysystems.backend.user.User;
import com.jazzysystems.backend.user.repository.UserRepository;

@Component
public class Authentication {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository;

    public String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Person getAuthenticatedPerson() {
        Optional<Person> optionalPerson = personRepository.findByEmail(this.getUserName());
        return optionalPerson.get();
    }

    public User getUser() {
        Person person = this.getAuthenticatedPerson();
        return this.userRepository.findUserByPerson(person).get();
    }
}
