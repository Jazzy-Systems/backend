package com.jazzysystems.backend.resident.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jazzysystems.backend.apartment.Apartment;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.resident.Resident;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    Optional<Resident> findByPerson(Person person);

    List<Resident> getFindByApartmentAndIsResident(Apartment apartment, Boolean isResident);

    Boolean existsByApartmentAndIsRepresentative(
            Apartment apartment, Boolean isRepresentative);
}
