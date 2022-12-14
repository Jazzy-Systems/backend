package com.jazzysystems.backend.communique.dto;

import java.time.LocalDate;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.typeCommunique.TypeCommunique;

import lombok.Data;

@Data
public class CommuniqueDTO {

    private Long communiqueId;

    private String titleCommunique;

    private LocalDate datePublished;

    private String description;

    private Person person;

    private TypeCommunique typeCommunique;

    private String typeName;
}
