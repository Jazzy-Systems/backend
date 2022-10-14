package com.jazzysystems.backend.communique.service;

import java.util.List;

import com.jazzysystems.backend.communique.Communique;
import com.jazzysystems.backend.communique.dto.CommuniqueDTO;
import com.jazzysystems.backend.typeCommunique.TypeCommunique;

public interface CommuniqueService {

    Communique save(CommuniqueDTO communiqueDTO);

    Communique update(Long communiqueId, CommuniqueDTO communiqueDTO);

    void delete(CommuniqueDTO communiqueDTO);

    void deleteById(Long communiqueId);

    Communique findById(Long communiqueId);

    List<Communique> findAll();

    List<Communique> findByTypeCommunique(TypeCommunique typeCommunique);

    Boolean existsById(Long communiqueId);
}
