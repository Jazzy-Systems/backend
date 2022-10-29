package com.jazzysystems.backend.pack.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jazzysystems.backend.exception.NoSuchElementFoundException;
import com.jazzysystems.backend.pack.Pack;
import com.jazzysystems.backend.pack.PackMapper;
import com.jazzysystems.backend.pack.dto.PackDTO;
import com.jazzysystems.backend.pack.repository.PackRepository;
import com.jazzysystems.backend.person.Person;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PackServiceImpl implements PackService {
    private final PackRepository packRepository;
    private final PackMapper packMapper;

    @Override
    public Pack savePack(PackDTO packDTO){
        Pack pack = packMapper.convertDTOtoPack(packDTO);
        LocalDateTime dateArrival = LocalDateTime.now();
        pack.setDateArrival(dateArrival);
        pack.setReceived(false);
        return packRepository.save(pack);
    }

    @Override
    public Pack updatePack(Long packId, PackDTO packDTO){
        Pack pack = this.findByIdPack(packId);
        LocalDateTime datePickedUp = LocalDateTime.now();
        pack.setDatePickedUp(datePickedUp);
        pack.setReceived(true);
        pack.setObservation(packDTO.getObservation());
        return packRepository.save(pack);
    }

    @Override
    public void deletePack(PackDTO packDTO){
        Pack pack = this.findByIdPack(packDTO.getPackId());
        packRepository.delete(pack);
    }

    @Override
    public void deleteByIdPack(Long packId){
        Pack pack = this.findByIdPack(packId);
        packRepository.delete(pack);
    }

    @Override
    public Pack findByIdPack(Long packId){
        Pack pack = packRepository.findById(
            packId).orElseThrow(
                    () -> new NoSuchElementFoundException("Pack Not Found"));
    return pack;
    }

    @Override
    public List<Pack> findAllPack(){
        return packRepository.findAll(Sort.by(Sort.Direction.DESC, "dateArrival"));
    }

    @Override
    public List<Pack> findAllPackByReceived(Boolean received){
        return packRepository.findByReceived(received);
    }

    @Override
    public List<Pack> findPacksByPerson(Person person){
        return packRepository.findByPerson(person);
    }

    @Override
    public Boolean existsPackById(Long packId){
        return packRepository.existsById(packId);
    }

}
