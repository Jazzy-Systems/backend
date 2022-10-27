package com.jazzysystems.backend.typeRequest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jazzysystems.backend.typeRequest.TypeRequest;

@Repository
public interface TypeRequestRepository extends JpaRepository<TypeRequest, Long> {

    Optional<TypeRequest> findByTypeRequestName(String typeRequestName);
}
