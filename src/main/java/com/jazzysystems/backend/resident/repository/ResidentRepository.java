package com.jazzysystems.backend.resident.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.resident.Resident;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    Optional<Resident> findByPerson(Person person);

}
