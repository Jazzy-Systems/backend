package com.jazzysystems.backend.apartment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Apartment", indexes = @Index(name = "uniqueApartment", columnList = "buildingName, apartmentNumber", unique = true))
public class Apartment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apartmentId;

    @Column(name = "buildingName", nullable = false)
    private String buildingName;

    @Column(name = "apartmentNumber", nullable = false)
    private String apartmentNumber;

    @Column(name = "codeApartment", nullable = false)
    private String codeApartment;
}
