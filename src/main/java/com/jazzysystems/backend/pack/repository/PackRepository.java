package com.jazzysystems.backend.pack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jazzysystems.backend.pack.Pack;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.securityguard.SecurityGuard;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {

    List<Pack> findByPerson(Person person);
    List<Pack> findByReceived(Boolean received);
    List<Pack> findByPerson(SecurityGuard securityGuard);
}
