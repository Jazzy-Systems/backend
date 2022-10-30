package com.jazzysystems.backend.communique.controller;

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

import com.jazzysystems.backend.communique.Communique;
import com.jazzysystems.backend.communique.dto.CommuniqueDTO;
import com.jazzysystems.backend.communique.service.CommuniqueService;
import com.jazzysystems.backend.util.emailSender.EmailService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/communique/")
public class CommuniqueController {

    @Autowired
    private final EmailService emailService;
    @Autowired
    private final CommuniqueService communiqueService;

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
        Communique communique = communiqueService.save(communiqueDTO);
        emailService.sendCommuniqueEmailtoAll(communique, "communique");
        return new ResponseEntity<Communique>(communique, HttpStatus.CREATED);
    }

    @PutMapping({ "/{communiqueId}" })
    public ResponseEntity<?> update(@PathVariable("communiqueId") Long communiqueId,
            @RequestBody CommuniqueDTO communiqueDTO) {
        Communique communique = communiqueService.update(communiqueId, communiqueDTO);
        return new ResponseEntity<Communique>(communique, HttpStatus.CREATED);
    }

    @DeleteMapping({ "/{communiqueId}" })
    public ResponseEntity<?> deleteById(@PathVariable("communiqueId") Long communiqueId) {
        communiqueService.deleteById(communiqueId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
