package com.jazzysystems.backend.typeCommunique.controller;

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

import com.jazzysystems.backend.typeCommunique.TypeCommunique;
import com.jazzysystems.backend.typeCommunique.dto.TypeCommuniqueDTO;
import com.jazzysystems.backend.typeCommunique.service.TypeCommuniqueService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/typeCommunique")
public class TypeCommuniqueController {
    private final TypeCommuniqueService typeCommuniqueService;

    @GetMapping(value = "")
    public ResponseEntity<?> findAllTypes() {
        return new ResponseEntity<>(typeCommuniqueService.findAll(), HttpStatus.OK);
    }

    @GetMapping({ "/{typeCommuniqueId}" })
    public ResponseEntity<?> findTypeCommunique(@PathVariable Long typeCommuniqueId) {
        TypeCommunique typeCommunique = typeCommuniqueService.findById(typeCommuniqueId);
        return new ResponseEntity<TypeCommunique>(typeCommunique, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody TypeCommuniqueDTO typeCommuniqueDTO) {
        TypeCommunique typeCommunique = typeCommuniqueService.save(typeCommuniqueDTO);
        return new ResponseEntity<TypeCommunique>(typeCommunique, HttpStatus.CREATED);
    }

    @PutMapping({ "/{typeCommuniqueId}" })
    public ResponseEntity<?> update(@PathVariable("typeCommuniqueId") Long typeCommuniqueId,
            @RequestBody TypeCommuniqueDTO typeCommuniqueDTO) {
        TypeCommunique typeCommunique = typeCommuniqueService.update(typeCommuniqueId, typeCommuniqueDTO);
        return new ResponseEntity<TypeCommunique>(typeCommunique, HttpStatus.OK);
    }

    @DeleteMapping({ "/{typeCommuniqueId}" })
    public ResponseEntity<?> deleteById(@PathVariable("typeCommuniqueId") Long typeCommuniqueId) {
        typeCommuniqueService.deleteById(typeCommuniqueId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
