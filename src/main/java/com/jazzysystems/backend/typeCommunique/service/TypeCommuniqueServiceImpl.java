package com.jazzysystems.backend.typeCommunique.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jazzysystems.backend.exception.NoSuchElementFoundException;
import com.jazzysystems.backend.typeCommunique.TypeCommunique;
import com.jazzysystems.backend.typeCommunique.dto.TypeCommuniqueDTO;
import com.jazzysystems.backend.typeCommunique.repository.TypeCommuniqueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TypeCommuniqueServiceImpl implements TypeCommuniqueService {

    private final TypeCommuniqueRepository typeCommuniqueRepository;

    @Override
    public TypeCommunique save(TypeCommuniqueDTO typeCommuniqueDTO) {
        TypeCommunique typeCommunique = new TypeCommunique();
        typeCommunique.setTypeName(typeCommuniqueDTO.getTypeName());
        return typeCommuniqueRepository.save(typeCommunique);
    }

    @Override
    public TypeCommunique update(Long typeCommuniqueId, TypeCommuniqueDTO typeCommuniqueDTO) {
        TypeCommunique typeCommunique = this.findById(typeCommuniqueId);
        typeCommunique.setTypeName(typeCommuniqueDTO.getTypeName());
        return typeCommuniqueRepository.save(typeCommunique);
    }

    @Override
    public void delete(TypeCommuniqueDTO typeCommuniqueDTO) {
        TypeCommunique typeCommunique = this.findById(typeCommuniqueDTO.getTypeCommuniqueId());
        typeCommuniqueRepository.delete(typeCommunique);
    }

    @Override
    public void deleteById(Long typeCommuniqueId) {
        typeCommuniqueRepository.deleteById(typeCommuniqueId);
    }

    @Override
    public TypeCommunique findById(Long typeCommuniqueId) {
        TypeCommunique typeCommunique = typeCommuniqueRepository.findById(
                typeCommuniqueId).orElseThrow(
                        () -> new NoSuchElementFoundException("Type Not Found"));
        return typeCommunique;
    }

    @Override
    public List<TypeCommunique> findAll() {
        return typeCommuniqueRepository.findAll();
    }

    @Override
    public TypeCommunique findByTypeName(String typeName) {
        TypeCommunique typeCommunique = typeCommuniqueRepository.findByTypeName(
                typeName).orElseThrow(
                        () -> new NoSuchElementFoundException("Type Not Found"));
        return typeCommunique;
    }

    @Override
    public Boolean existsById(Long typeCommuniqueId) {
        return typeCommuniqueRepository.existsById(typeCommuniqueId);
    }

}
