package com.jazzysystems.backend.typeRequest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jazzysystems.backend.typeRequest.TypeRequest;
import com.jazzysystems.backend.typeRequest.dto.TypeRequestDTO;
import com.jazzysystems.backend.typeRequest.service.TypeRequestService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RequestMapping("/api/v1/typerequest/")
public class TypeRequestController {

    @Autowired
    private TypeRequestService typeRequestService;

    @GetMapping(value = "")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(typeRequestService.findAll(), HttpStatus.OK);
    }

    @GetMapping({ "/{id}" })
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        TypeRequest typeRequest = typeRequestService.findById(id);
        return new ResponseEntity<TypeRequest>(typeRequest, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody TypeRequestDTO typeRequestDTO) {
        TypeRequest typeRequest = typeRequestService.save(typeRequestDTO);
        return new ResponseEntity<TypeRequest>(typeRequest, HttpStatus.CREATED);
    }

}
