package com.jazzysystems.backend.communique.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jazzysystems.backend.communique.Communique;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.typeCommunique.TypeCommunique;

@Repository
public interface CommuniqueRepository extends JpaRepository<Communique, Long> {

    List<Communique> findByPerson(Person person);

    List<Communique> findByTypeCommunique(TypeCommunique typeCommunique);

    List<Communique> findByDatePublished(LocalDate datePublished);

}
