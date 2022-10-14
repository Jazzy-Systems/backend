package com.jazzysystems.backend.typeCommunique;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TypeCommunique", indexes = @Index(name = "uniqueTypeName", columnList = "typeName", unique = true))
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class TypeCommunique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeCommuniqueId;

    @Column(name = "typeName", nullable = false)
    private String typeName;
}
