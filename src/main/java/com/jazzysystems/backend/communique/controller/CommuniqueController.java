package com.jazzysystems.backend.communique.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jazzysystems.backend.communique.Communique;
import com.jazzysystems.backend.communique.dto.CommuniqueDTO;
import com.jazzysystems.backend.communique.service.CommuniqueService;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.repository.PersonRepository;
import com.jazzysystems.backend.typeCommunique.TypeCommunique;
import com.jazzysystems.backend.typeCommunique.repository.TypeCommuniqueRepository;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/communique")
public class CommuniqueController {

    private final CommuniqueService communiqueService;

    private final PersonRepository personRepository;

    private final TypeCommuniqueRepository typeCommuniqueRepository;

    @GetMapping(value = "")
    public ResponseEntity<?> findAllCommuniques() {
        return new ResponseEntity<>(communiqueService.findAll(), HttpStatus.OK);
    }

    @GetMapping({ "/{communiqueId}" })
    public ResponseEntity<?> findCommunique(@PathVariable Long communiqueId) {
        Communique communique = communiqueService.findById(communiqueId);
        return new ResponseEntity<Communique>(communique, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody CommuniqueDTO communiqueDTO) {
        String emailOrusername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Person> optionalPerson = personRepository.findByEmail(emailOrusername);
        Optional<TypeCommunique> optionalTypeCommunique = typeCommuniqueRepository
                .findByTypeName(communiqueDTO.getTypeName());
        if (optionalPerson.isPresent() && optionalTypeCommunique.isPresent()) {
            Person person = optionalPerson.get();
            TypeCommunique typeCommunique = optionalTypeCommunique.get();
            communiqueDTO.setPerson(person);
            communiqueDTO.setTypeCommunique(typeCommunique);
            Communique communique = communiqueService.save(communiqueDTO);
            System.out.println(communique.getDescription());
            return new ResponseEntity<Communique>(communique, HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("Error Bad Request, please check Person and type", HttpStatus.BAD_REQUEST);
    }

    // TODO improve implementations
    @PutMapping({ "/{communiqueId}" })
    public ResponseEntity<?> update(@PathVariable("communiqueId") Long communiqueId,
            @RequestBody CommuniqueDTO communiqueDTO) {
        String emailOrusername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Person> optionalPerson = personRepository.findByEmail(emailOrusername);
        Optional<TypeCommunique> optionalTypeCommunique = typeCommuniqueRepository
                .findByTypeName(communiqueDTO.getTypeName());
        if (optionalPerson.isPresent() && optionalTypeCommunique.isPresent()) {
            Person person = optionalPerson.get();
            TypeCommunique typeCommunique = optionalTypeCommunique.get();
            communiqueDTO.setPerson(person);
            communiqueDTO.setTypeCommunique(typeCommunique);
            Communique communique = communiqueService.update(communiqueId, communiqueDTO);
            return new ResponseEntity<Communique>(communique, HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("Bad Request please check the fields", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping({ "/{communiqueId}" })
    public ResponseEntity<?> deleteById(@PathVariable("communiqueId") Long communiqueId) {
        communiqueService.deleteById(communiqueId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
