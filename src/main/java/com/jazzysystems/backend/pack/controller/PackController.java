package com.jazzysystems.backend.pack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jazzysystems.backend.auth.Authentication;
import com.jazzysystems.backend.pack.Pack;
import com.jazzysystems.backend.pack.PackMapper;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.pack.dto.PackDTO;
import com.jazzysystems.backend.pack.service.PackService;
import com.jazzysystems.backend.person.service.PersonService;
import com.jazzysystems.backend.util.emailSender.EmailService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pack/")
public class PackController {

    @Autowired
    private final EmailService emailService;
    @Autowired
    private final PackService packService;

    @Autowired
    private final Authentication authentication;

    @Autowired
    private final PersonService personService;

    @Autowired
    private final PackMapper packMapper;

    @GetMapping(value = "")
    public ResponseEntity<?> findAllPacks() {
        return new ResponseEntity<>(packService.findAllPack(), HttpStatus.OK);
    }

    @GetMapping({ "/{packId}" })
    public ResponseEntity<?> findPack(@PathVariable Long packId) {
        Pack pack = packService.findByIdPack(packId);
        return new ResponseEntity<Pack>(pack, HttpStatus.OK);
    }

    @GetMapping({ "/person/{personId}" })
    public ResponseEntity<?> findPacksByPerson(@PathVariable Long personId) {
        List<Pack> pack = packService.findPacksByPerson(personService.findById(personId));
        return new ResponseEntity<>(pack, HttpStatus.OK);
    }

    @GetMapping({ "/all/{apartmentId}" })
    public ResponseEntity<?> findPacksByAparment(@PathVariable Long apartmentId) {
        List<PackDTO> listpackDTO = packService.findAllPackApartament(apartmentId);
        return new ResponseEntity<>(listpackDTO, HttpStatus.OK);
    }

    @GetMapping({ "/mypacks" })
    public ResponseEntity<?> findMyPacksByPerson() {
        Person person = authentication.getAuthenticatedPerson();

        List<Pack> pack = packService.findPacksByPerson(person);
        return new ResponseEntity<>(pack, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody PackDTO packDTO) {

        Pack pack = packService.savePack(packDTO);
        packDTO = packMapper.convertPackToDTO(pack);
        emailService.sendPackNotification(pack.getPerson(), pack);
        return new ResponseEntity<PackDTO>(packDTO, HttpStatus.CREATED);
    }

    @PutMapping({ "/{packId}" })
    public ResponseEntity<?> update(@PathVariable("packId") Long packId, @RequestBody PackDTO packDTO) {
        Pack pack = packService.updatePack(packId, packDTO);
        packDTO = packMapper.convertPackToDTO(pack);
        return new ResponseEntity<PackDTO>(packDTO, HttpStatus.CREATED);
    }

    @DeleteMapping({ "/{packId}" })
    public ResponseEntity<?> deleteById(@PathVariable("packId") Long packId) {
        packService.deleteByIdPack(packId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
