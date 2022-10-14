package com.jazzysystems.backend.typeCommunique.service;

import java.util.List;

import com.jazzysystems.backend.typeCommunique.TypeCommunique;
import com.jazzysystems.backend.typeCommunique.dto.TypeCommuniqueDTO;

public interface TypeCommuniqueService {

    TypeCommunique save(TypeCommuniqueDTO typeCommuniqueDTO);

    TypeCommunique update(Long typeCommuniqueId, TypeCommuniqueDTO typeCommuniqueDTO);

    void delete(TypeCommuniqueDTO typeCommuniqueDTO);

    void deleteById(Long typeCommuniqueId);

    TypeCommunique findById(Long typeCommuniqueId);

    List<TypeCommunique> findAll();

    TypeCommunique findByTypeName(
            String typeName);

    Boolean existsById(Long typeCommuniqueId);
}
