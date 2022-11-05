package com.jazzysystems.backend.pack.service;

import java.util.List;

import com.jazzysystems.backend.pack.Pack;
import com.jazzysystems.backend.pack.dto.PackDTO;
import com.jazzysystems.backend.person.Person;

public interface PackService {
    Pack savePack(PackDTO packDTO);

    Pack updatePack(Long packId, PackDTO packDTO);

    void deletePack(PackDTO packDTO);

    void deleteByIdPack(Long packId);

    Pack findByIdPack(Long packId);

    List<PackDTO> findAllPack();

    List<Pack> findAllPackByReceived(Boolean received);

    List<PackDTO> findAllPackApartament(Long apartmentId);

    List<Pack> findPacksByPerson(Person person);

    Boolean existsPackById(Long packId);
}
