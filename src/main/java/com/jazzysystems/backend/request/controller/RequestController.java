package com.jazzysystems.backend.request.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.service.PersonService;
import com.jazzysystems.backend.request.Request;
import com.jazzysystems.backend.request.dto.RequestDTO;
import com.jazzysystems.backend.request.service.RequestService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/request/")
public class RequestController {
    @Autowired
    private final RequestService requestService;

    @Autowired
    private final PersonService personService;

    @GetMapping(value = "")
    public ResponseEntity<?> findAllRequest() {
        return new ResponseEntity<>(requestService.findAllRequest(), HttpStatus.OK);
    }   
    
    @GetMapping(value = "/person/{persondni}")
    public ResponseEntity<?> findRequestsByPerson(@PathVariable Long persondni) {        
        return new ResponseEntity<>(requestService.findRequestsByPerson(persondni), HttpStatus.OK);
    } 

    @GetMapping(value = "myallrequest")
    public ResponseEntity<?> findRequestsByPerson() {
        String emailOrusername = SecurityContextHolder.getContext().getAuthentication().getName();
        Person person = personService.findPersonByEmail(emailOrusername);
        return new ResponseEntity<>(requestService.findRequestsByPerson(person.getDni()), HttpStatus.OK);
    } 

    @GetMapping(value = "forstatus/{status}")
    public ResponseEntity<?> findAllByPersonAndStatusRequest(@PathVariable Boolean status) {
        String emailOrusername = SecurityContextHolder.getContext().getAuthentication().getName();
        Person person = personService.findPersonByEmail(emailOrusername);
        return new ResponseEntity<>(requestService.findAllByPersonAndStatusRequest(person,status), HttpStatus.OK);
    }
    
    
    @PostMapping()
    public ResponseEntity<?> save(@RequestBody RequestDTO requestDTO) {
            Request request = requestService.saveRequest(requestDTO);
            return new ResponseEntity<Request>(request, HttpStatus.CREATED);
    }
    
    @PutMapping({ "/{packId}" })
    public ResponseEntity<?> update(@PathVariable("packId") Long requestId, @RequestBody RequestDTO requestDTO) {
        Request request = requestService.updateRequest(requestId, requestDTO);
        return new ResponseEntity<Request>(request, HttpStatus.CREATED);       
    }
}
