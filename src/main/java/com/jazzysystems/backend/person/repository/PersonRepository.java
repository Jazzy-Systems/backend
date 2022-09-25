package com.jazzysystems.backend.person.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jazzysystems.backend.person.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findPersonByDni(long dni);
}
