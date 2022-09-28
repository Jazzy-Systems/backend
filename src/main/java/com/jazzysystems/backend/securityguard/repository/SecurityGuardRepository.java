package com.jazzysystems.backend.securityguard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.securityguard.SecurityGuard;

@Repository
public interface SecurityGuardRepository extends JpaRepository <SecurityGuard, Long>{
    Optional<SecurityGuard> findByPerson(Person person);
}
