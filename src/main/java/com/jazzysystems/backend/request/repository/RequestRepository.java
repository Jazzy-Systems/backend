package com.jazzysystems.backend.request.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.request.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByPerson(Person person);
}
