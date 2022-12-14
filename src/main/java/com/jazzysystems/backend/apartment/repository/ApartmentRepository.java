package com.jazzysystems.backend.apartment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jazzysystems.backend.apartment.Apartment;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    Optional<Apartment> findByBuildingNameAndApartmentNumber(
            String buildingName, String number);

    Optional<Apartment> findByCodeApartment(String codeApartment);

    Boolean existsByCodeApartment(String codeApartment);

}
