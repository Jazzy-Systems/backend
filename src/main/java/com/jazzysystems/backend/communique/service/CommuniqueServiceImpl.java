package com.jazzysystems.backend.communique.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jazzysystems.backend.communique.Communique;
import com.jazzysystems.backend.communique.dto.CommuniqueDTO;
import com.jazzysystems.backend.communique.repository.CommuniqueRepository;
import com.jazzysystems.backend.exception.NoSuchElementFoundException;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.repository.PersonRepository;
import com.jazzysystems.backend.typeCommunique.TypeCommunique;
import com.jazzysystems.backend.typeCommunique.repository.TypeCommuniqueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommuniqueServiceImpl implements CommuniqueService {

    @Autowired
    private final CommuniqueRepository communiqueRepository;
    @Autowired
    private final PersonRepository personRepository;
    @Autowired
    private final TypeCommuniqueRepository typeCommuniqueRepository;

    @Override
    public Communique save(CommuniqueDTO communiqueDTO) {
        Communique communique = new Communique();
        LocalDate datePublished = LocalDate.now();
        // TODO create an Util class that return the authenticated person
        String emailOrusername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Person> optionalPerson = personRepository.findByEmail(emailOrusername);
        Optional<TypeCommunique> optionalTypeCommunique = typeCommuniqueRepository
                .findByTypeName(communiqueDTO.getTypeName());
        if (optionalPerson.isPresent() && optionalTypeCommunique.isPresent()) {
            communiqueDTO.setPerson(optionalPerson.get());
            communiqueDTO.setTypeCommunique(optionalTypeCommunique.get());
        }
        communique.setDatePublished(datePublished);
        communique.setDescription(communiqueDTO.getDescription());
        communique.setPerson(communiqueDTO.getPerson());
        communique.setTitleCommunique(communiqueDTO.getTitleCommunique());
        communique.setTypeCommunique(communiqueDTO.getTypeCommunique());
        return communiqueRepository.save(communique);
    }

    @Override
    public Communique update(Long communiqueId, CommuniqueDTO communiqueDTO) {
        String emailOrusername = SecurityContextHolder.getContext().getAuthentication().getName();
        // TODO check if is posible to remove the optional verification and let Jpa
        // throw DataConstraintViolation
        Optional<Person> optionalPerson = personRepository.findByEmail(emailOrusername);
        Optional<TypeCommunique> optionalTypeCommunique = typeCommuniqueRepository
                .findByTypeName(communiqueDTO.getTypeName());
        if (optionalPerson.isPresent() && optionalTypeCommunique.isPresent()) {
            communiqueDTO.setPerson(optionalPerson.get());
            communiqueDTO.setTypeCommunique(optionalTypeCommunique.get());
        }
        // TODO mappers of communique
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
