package com.jazzysystems.backend.typeCommunique.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jazzysystems.backend.typeCommunique.TypeCommunique;

@Repository
public interface TypeCommuniqueRepository extends JpaRepository<TypeCommunique, Long> {

    Optional<TypeCommunique> findByTypeName(String typeName);

}
