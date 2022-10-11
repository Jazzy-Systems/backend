package com.jazzysystems.backend.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<User> findUserByPerson(Person person);

}
