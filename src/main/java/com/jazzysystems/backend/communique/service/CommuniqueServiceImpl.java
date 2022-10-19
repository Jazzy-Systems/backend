package com.jazzysystems.backend.communique.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jazzysystems.backend.communique.Communique;
import com.jazzysystems.backend.communique.dto.CommuniqueDTO;
import com.jazzysystems.backend.communique.repository.CommuniqueRepository;
import com.jazzysystems.backend.exception.NoSuchElementFoundException;
import com.jazzysystems.backend.typeCommunique.TypeCommunique;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommuniqueServiceImpl implements CommuniqueService {

    private final CommuniqueRepository communiqueRepository;

    @Override
    public Communique save(CommuniqueDTO communiqueDTO) {
        Communique communique = new Communique();
        LocalDate datePublished = LocalDate.now();
        communique.setDatePublished(datePublished);
        communique.setDescription(communiqueDTO.getDescription());
        communique.setPerson(communiqueDTO.getPerson());
        communique.setTitleCommunique(communiqueDTO.getTitleCommunique());
        communique.setTypeCommunique(communiqueDTO.getTypeCommunique());
        return communiqueRepository.save(communique);
    }

    @Override
    public Communique update(Long communiqueId, CommuniqueDTO communiqueDTO) {
        Communique communique = this.findById(communiqueId);
        communique.setDatePublished(communiqueDTO.getDatePublished());
        communique.setDescription(communiqueDTO.getDescription());
        communique.setPerson(communiqueDTO.getPerson());
        communique.setTitleCommunique(communiqueDTO.getTitleCommunique());
        communique.setTypeCommunique(communiqueDTO.getTypeCommunique());
        return communiqueRepository.save(communique);
    }

    @Override
    public void delete(CommuniqueDTO communiqueDTO) {
        Communique communique = this.findById(communiqueDTO.getCommuniqueId());
        communiqueRepository.delete(communique);
    }

    @Override
    public void deleteById(Long communiqueId) {
        communiqueRepository.deleteById(communiqueId);
    }

    @Override
    public Communique findById(Long communiqueId) {
        Communique communique = communiqueRepository.findById(
                communiqueId).orElseThrow(
                        () -> new NoSuchElementFoundException("Communique Not Found"));
        return communique;
    }

    @Override
    public List<Communique> findAll() {
        return communiqueRepository.findAll(Sort.by(Sort.Direction.DESC, "datePublished"));
    }

    @Override
    public List<Communique> findByTypeCommunique(TypeCommunique typeCommunique) {
        return communiqueRepository.findByTypeCommunique(typeCommunique);
    }

    @Override
    public Boolean existsById(Long communiqueId) {
        return communiqueRepository.existsById(communiqueId);
    }

}
