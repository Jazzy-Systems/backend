package com.jazzysystems.backend.typeRequest.service;

import java.util.List;

import com.jazzysystems.backend.typeRequest.TypeRequest;
import com.jazzysystems.backend.typeRequest.dto.TypeRequestDTO;

public interface TypeRequestService {

    TypeRequest findById(Long id);

    Boolean existsById(Long id);

    TypeRequest save(TypeRequestDTO typeRequestDTO);

    TypeRequest update(Long id, TypeRequestDTO typeRequestDTO);

    TypeRequest findByTypeRequestName(String typeRequestName);

    List<TypeRequest> findAll();

    void deleteById(Long id);

}
