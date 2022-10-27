package com.jazzysystems.backend.typeRequest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jazzysystems.backend.exception.NoSuchElementFoundException;
import com.jazzysystems.backend.typeRequest.TypeRequest;
import com.jazzysystems.backend.typeRequest.TypeRequestMapper;
import com.jazzysystems.backend.typeRequest.dto.TypeRequestDTO;
import com.jazzysystems.backend.typeRequest.repository.TypeRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TypeRequestServiceImpl implements TypeRequestService {

    private final TypeRequestRepository typeRequestRepository;

    private final TypeRequestMapper typeRequestMapper;

    @Override
    public TypeRequest findById(Long id) {
        TypeRequest typeRequest = typeRequestRepository.findById(id).orElseThrow(
                () -> new NoSuchElementFoundException("TypeRequest Not Found"));
        return typeRequest;
    }

    @Override
    public Boolean existsById(Long id) {
        return typeRequestRepository.existsById(id);
    }

    @Override
    public TypeRequest save(TypeRequestDTO typeRequestDTO) {
        TypeRequest typeRequest = typeRequestMapper.convertDTOtoObject(typeRequestDTO);
        return typeRequestRepository.save(typeRequest);
    }

    @Override
    public TypeRequest update(Long id, TypeRequestDTO typeRequestDTO) {
        TypeRequest typeRequest = typeRequestRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("TypeRequest Not Found"));
        typeRequest.setTypeRequestName(typeRequestDTO.getTypeRequestName());
        return typeRequest;
    }

    @Override
    public TypeRequest findByTypeRequestName(String typeRequestName) {
        TypeRequest typeRequest = typeRequestRepository.findByTypeRequestName(typeRequestName)
                .orElseThrow(() -> new NoSuchElementFoundException("TypeRequest Not Found"));
        return typeRequest;
    }

    @Override
    public void deleteById(Long id) {
        typeRequestRepository.deleteById(id);
    }

    @Override
    public List<TypeRequest> findAll() {
        return typeRequestRepository.findAll();
    }

}
